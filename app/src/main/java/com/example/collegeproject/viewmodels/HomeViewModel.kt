package com.example.collegeproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.utils.UserPreferences
import com.example.collegeproject.utils.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userDao: UserDao,
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun getUserType(): String {
        var userType = ""
        uiScope.launch {
            userType = userPreferencesRepository.loggedInAs()!!
        }
        return if (userType == "Seller") "Buyers" else "Sellers"
    }

    fun clearUserPreferences() {
        CoroutineScope(Dispatchers.IO).launch {
            userPreferencesRepository.updateUserPreferences(UserPreferences())
        }
    }
}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val dataSource: UserDao,
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataSource, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}