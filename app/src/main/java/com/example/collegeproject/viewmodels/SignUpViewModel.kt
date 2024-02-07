package com.example.collegeproject.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    var businessName by mutableStateOf("")
        private set

    fun onBusinessNameChange(businessName: String) {
        this.businessName = businessName
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
    }

    var password by mutableStateOf("")
        private set

    fun onPasswordChange(password: String) {
        this.password = password
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