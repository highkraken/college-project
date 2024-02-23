package com.example.collegeproject.utils

import androidx.compose.ui.text.toUpperCase
import androidx.navigation.NavController
import java.util.Locale

fun NavController.navigateWithPop(destination: String, popupTo: String?) {
    this.navigate(destination) {
        if (popupTo != null) {
            popUpTo(popupTo) {
                inclusive = true
            }
        }
    }
}

fun String.toUnitType(): UnitType {
    val name = this.split(" ").dropLast(1).joinToString("_").uppercase()
    return if (name.isNotEmpty()) UnitType.valueOf(name) else UnitType.NONE
}