package com.example.collegeproject.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
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

    @Query("SELECT * FROM purchase_sell_invoice WHERE invoice_date = :date")
    fun getAllEntries(date: LocalDate = LocalDate.now()): LiveData<List<PurchaseSale>>

    @Query("SELECT * FROM purchase_sell_invoice WHERE seller_name = :sellerName AND product_name = :productName")
    fun getItemsOfSeller(sellerName: String, productName: String): LiveData<List<PurchaseSale>>
}