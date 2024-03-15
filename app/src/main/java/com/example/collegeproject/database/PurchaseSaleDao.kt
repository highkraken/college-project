package com.example.collegeproject.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.collegeproject.model.PurchaseProductResult
import java.time.LocalDate

@Dao
interface PurchaseSaleDao {
    @Upsert
    fun upsertPurchaseSaleEntry(purchaseSaleEntry: PurchaseSale)

    @Upsert
    fun upsertPurchaseSaleEntry(purchaseSaleEntries: List<PurchaseSale>)

    @Delete
    fun deletePurchaseSaleEntry(purchaseSaleEntry: PurchaseSale)

    @Delete
    fun deletePurchaseSaleEntry(purchaseSaleEntries: List<PurchaseSale>)

    @Query("SELECT * FROM purchase_sale_invoice WHERE invoice_date = :date")
    fun getAllEntries(date: LocalDate = LocalDate.now()): LiveData<List<PurchaseSale>>

    @Query("SELECT * FROM purchase_sale_invoice WHERE seller_id = :sellerId AND product_id = :productId AND invoice_date = :date")
    fun getItemsOfSeller(sellerId: Long, productId: Long, date: LocalDate = LocalDate.now()): LiveData<List<PurchaseSale>>

    @Query("SELECT product_id AS productId, SUM(product_unit) AS productUnit, product_name AS productName, SUM(product_quantity) AS productQuantity, " +
            "product_price AS productPrice, SUM(product_total) AS productTotal, p_wage AS labour, p_wage_type AS labourType, p_commission AS commission, " +
            "p_commission_type AS commissionType, p_import_fare AS importFare, p_import_fare_type AS importFareType, p_extra_expense AS extraExpense, " +
            "p_extra_expense_type AS extraExpenseType FROM purchase_sale_invoice " +
            "WHERE seller_id = :sellerId AND invoice_date = :date " +
            "GROUP BY product_id, product_price " +
            "ORDER BY product_price DESC, product_name ASC")
    fun getProductsOfSeller(sellerId: Long, date: LocalDate = LocalDate.now()): LiveData<List<PurchaseProductResult>>
}