package com.example.collegeproject.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    fun onEmailChange(email: String) {
        this.email = email
    }

    var password by mutableStateOf("")
        private set

    fun onPasswordChange(password: String) {
        this.password = password
    }

    private val _eventSignUpClick = MutableLiveData<Boolean>()
    val eventSignUpClick: LiveData<Boolean>
        get() = _eventSignUpClick

    init {
        _eventSignUpClick.value = false
    }

    fun onLoginClickEvent() {
        _eventSignUpClick.value = true
    }

    fun onLoginClickEventOver() {
        _eventSignUpClick.value = false
    }
}