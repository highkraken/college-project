package com.example.collegeproject.model

import com.example.collegeproject.utils.UnitType

data class SaleProductResult(
    val productId: Long,
    val productUnit: Float,
    val productName: String,
    val productQuantity: Float,
    val productPrice: Float,
    val productTotal: Float,
    val labour: Float,
    val labourType: UnitType,
    val extraExpense: Float,
    val extraExpenseType: UnitType,
) {
    val labourValue: Float
        get() = calculateValue(labourType, labour)

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
