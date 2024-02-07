package com.example.collegeproject.utils

sealed class Screen(val route: String) {
    data object SignUp : Screen("sign_up")
    data object Login : Screen("login")
}