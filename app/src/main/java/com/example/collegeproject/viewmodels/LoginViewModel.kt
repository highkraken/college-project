package com.example.collegeproject.viewmodels

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.collegeproject.database.User
import com.example.collegeproject.database.UserDatabaseDao
import com.example.collegeproject.utils.ValidationError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    val userDatabaseDao: UserDatabaseDao
) : ViewModel() {
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var emailError by mutableStateOf(ValidationError.NONE)
        private set

    var passwordError by mutableStateOf(ValidationError.NONE)
        private set

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

    var password by mutableStateOf("")
        private set

    fun onPasswordChange(password: String) {
        this.password = password
        passwordError = when {
            password.isEmpty() -> ValidationError.EMPTY
            else -> ValidationError.NONE
        }
    }

    private val _eventSignUpClick = MutableLiveData<Boolean>()
    val eventSignUpClick: LiveData<Boolean>
        get() = _eventSignUpClick

    private val _eventLoginClick = MutableLiveData<Boolean>()
    val eventLoginClick: LiveData<Boolean>
        get() = _eventLoginClick

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    init {
        _eventSignUpClick.value = false
        _eventLoginClick.value = false
        _toastMessage.value = ""
    }

    fun onLoginClickEvent() {
//        _eventLoginClick.value = true
        if (!validRequiredFields()) return
        uiScope.launch {
            val user = getUser()
            if (user != null) {
                if (user.password == password) _toastMessage.value = "Login successful"
                else passwordError = ValidationError.WRONG_PASSWORD
            } else {
                _toastMessage.value = "Credentials wrong"
                emailError = ValidationError.EMAIL_NOT_FOUND
            }
        }
    }

    fun onLoginClickEventOver() {
        _eventLoginClick.value = false
    }

    private fun validRequiredFields(): Boolean {
        return emailError == ValidationError.NONE && passwordError == ValidationError.NONE
    }

    private suspend fun getUser(): User? {
        return withContext(Dispatchers.IO) {
            userDatabaseDao.getUserByEmail(email)
        }
    }

    fun onSignUpClickEvent() {
        _eventSignUpClick.value = true
    }

    fun onSignUpClickEventOver() {
        _eventSignUpClick.value = false
    }

    fun clearToastMessage() {
        _toastMessage.value = ""
    }
}