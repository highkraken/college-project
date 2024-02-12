package com.example.collegeproject.viewmodels

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.collegeproject.database.User
import com.example.collegeproject.database.UserDatabaseDao
import com.example.collegeproject.utils.UserPreferences
import com.example.collegeproject.utils.UserPreferencesRepository
import com.example.collegeproject.utils.ValidationError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(
    private val userDatabaseDao: UserDatabaseDao,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var businessName by mutableStateOf("")
        private set

    fun onBusinessNameChange(businessName: String) {
        this.businessName = businessName
        businessNameError = if (businessName.isNotEmpty()) ValidationError.NONE else ValidationError.EMPTY
    }

    var businessAddress by mutableStateOf("")
        private set

    fun onBusinessAddressChange(businessAddress: String) {
        this.businessAddress = businessAddress
    }

    var ownerName by mutableStateOf("")
        private set

    fun onOwnerNameChange(ownerName: String) {
        this.ownerName = ownerName
    }

    var email by mutableStateOf("")
        private set

    fun onEmailChange(email: String) {
        this.email = email
        emailError = when {
            email.isEmpty() -> ValidationError.EMPTY
            email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationError.EMAIL_ERROR
            else -> ValidationError.NONE
        }
    }

    var phoneNumber by mutableStateOf("")
        private set

    fun onPhoneNumberChange(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }

    var userType by mutableStateOf("")
        private set

    fun onUserTypeChange(userType: String) {
        this.userType = userType
        userTypeError = if (userType.isEmpty()) ValidationError.EMPTY else ValidationError.NONE
    }

    var password by mutableStateOf("")
        private set

    fun onPasswordChange(password: String) {
        this.password = password
        passwordError = when {
            password.isEmpty() -> ValidationError.EMPTY
            password.isNotEmpty() && !isValidPassword() -> ValidationError.WEAK_PASSWORD
            else -> ValidationError.NONE
        }
    }

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    fun cleatToastMessage() {
        _toastMessage.value = ""
    }

    private var _userByEmail = MutableLiveData<User>()

    var businessNameError by mutableStateOf(ValidationError.NONE)
        private set

    var emailError by mutableStateOf(ValidationError.NONE)
        private set

    var passwordError by mutableStateOf(ValidationError.NONE)
        private set

    var userTypeError by mutableStateOf(ValidationError.EMPTY)
        private set

    init {
        _toastMessage.value = ""
    }

    fun onSignUpClickEvent() {
        if (!validRequiredFields()) return
        uiScope.launch {
            val user = User(
                businessName = businessName,
                emailId = email,
                password = password,
                userType = userType,
                businessAddress = businessAddress,
                phoneNumber = phoneNumber,
                ownerName = ownerName
            )
            insertUser(user)
        }
    }

    private suspend fun insertUser(user: User) {
        return withContext(Dispatchers.IO) {
            val old = userDatabaseDao.getUserByEmail(email)
            if (old == null) {
                userDatabaseDao.upsertUser(user)
                _toastMessage.postValue("Sign up was successful")
                userPreferencesRepository.updateUserPreferences(
                    UserPreferences(user.userId, businessName, businessAddress, ownerName, email, phoneNumber, userType)
                )
            } else {
                Log.d("SINGUPDB", old.toString())
                _toastMessage.postValue("Email already used")
            }
        }
    }

    private fun validRequiredFields(): Boolean =
        businessNameError == ValidationError.NONE &&
        emailError == ValidationError.NONE &&
        passwordError == ValidationError.NONE &&
        userTypeError == ValidationError.NONE

    private fun isValidPassword(): Boolean {
        val pattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,}$")
        return pattern.matches(password)
    }

    fun toMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["BusinessName"] = businessName
        map["BusinessAddress"] = businessAddress
        map["OwnerName"] = ownerName
        map["Email"] = email
        map["PhoneNumber"] = phoneNumber
        map["UserType"] = userType
        map["Password"] = password
        return map
    }
}

class SignUpViewModelFactory(
    private val dataSource: UserDatabaseDao,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(dataSource, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}