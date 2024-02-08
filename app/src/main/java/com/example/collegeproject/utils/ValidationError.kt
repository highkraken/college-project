package com.example.collegeproject.utils

enum class ValidationError(val errorMessage: String) {
    NONE(""),
    EMPTY(" is a required field"),
    WEAK_PASSWORD("Must be 8 characters long and include at least 1 lowercase, 1 uppercase, 1 number and 1 symbol"),
    EMAIL_ERROR("Email is not valid"),
    EMAIL_NOT_FOUND("Email not found, please sign up"),
    WRONG_PASSWORD("Wrong password"),
}