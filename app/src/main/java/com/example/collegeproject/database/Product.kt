package com.example.collegeproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.collegeproject.utils.UnitType

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("product_id")
    val productId: Long = 0L,
    
    @ColumnInfo("product_name")
    val productName: String,

    @ColumnInfo("s_wage")
    val saleWage: Float = 0f,

    @ColumnInfo("s_wage_type")
    val saleWageType: UnitType = UnitType.NONE,

    @ColumnInfo("s_extra_expense")
    val saleExtraExpense: Float = 0f,

    @ColumnInfo("s_extra_expense_type")
    val saleExtraExpenseType: UnitType = UnitType.NONE,

    @ColumnInfo("p_wage")
    val purchaseWage: Float = 0f,

    @ColumnInfo("p_wage_type")
    val purchaseWageType: UnitType = UnitType.NONE,

    @ColumnInfo("p_commission")
    val purchaseCommission: Float = 0f,

    @ColumnInfo("p_commission_type")
    val purchaseCommissionType: UnitType = UnitType.NONE,

    @ColumnInfo("p_import_fare")
    val purchaseImportFare: Float = 0f,

    @ColumnInfo("p_import_fare_type")
    val purchaseImportFareType: UnitType = UnitType.NONE,

    @ColumnInfo("p_extra_expense")
    val purchaseExtraExpense: Float = 0f,

    @ColumnInfo("p_extra_expense_type")
    val purchaseExtraExpenseType: UnitType = UnitType.NONE
)
