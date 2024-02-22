package com.example.collegeproject.utils

import androidx.navigation.NavController

fun NavController.navigateWithPop(destination: String, popupTo: String?) {
    this.navigate(destination) {
        if (popupTo != null) {
            popUpTo(popupTo) {
                inclusive = true
            }
        }
    }
}