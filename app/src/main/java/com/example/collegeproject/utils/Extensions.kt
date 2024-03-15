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

fun String.toPriceType(): PriceType {
    val name = this.split(" ")[0]
    val type = when (name.toIntOrNull()) {
        20 -> "TWENTY"
        10 -> "TEN"
        5 -> "FIVE"
        1 -> "ONE"
        null -> name.uppercase()
        else -> ""
    }
    return PriceType.valueOf(type)
}

fun <T> Iterable<T>.sumOf(selector: (T) -> Float): Float {
    var sum: Float = 0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}