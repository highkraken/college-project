package com.example.collegeproject.model

import com.example.collegeproject.utils.UnitType

data class PurchaseProductResult(
    val productId: Long,
    val productUnit: Float,
    val productName: String,
    val productQuantity: Float,
    val productPrice: Float,
    val productTotal: Float,
    val labour: Float,
    val labourType: UnitType,
    val commission: Float,
    val commissionType: UnitType,
    val importFare: Float,
    val importFareType: UnitType,
    val extraExpense: Float,
    val extraExpenseType: UnitType,
) {
    val commissionValue: Float
        get() = calculateValue(commissionType, commission)

    val labourValue: Float
        get() = calculateValue(labourType, labour)

    val importFareValue: Float
        get() = calculateValue(importFareType, importFare)

    val extraExpenseValue: Float
        get() = calculateValue(extraExpenseType, extraExpense)

    private fun calculateValue(type: UnitType, value: Float): Float {
        return when (type) {
            UnitType.NONE -> 0f
            UnitType.PERCENT -> productTotal * value / 100
            else -> productUnit * value
        }
    }
}