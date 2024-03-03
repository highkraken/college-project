package com.example.collegeproject.utils

enum class PriceType(val type: String) {
    TWENTY("20 qty"),
    TEN("10 qty"),
    FIVE("5 qty"),
    ONE("1 qty"),
    UNIT("Unit"),
    AMOUNT("Amount");

    fun toMenuItem(): String {
        return this.type
    }
}