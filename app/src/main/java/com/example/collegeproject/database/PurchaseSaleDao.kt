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

    @Query("SELECT * FROM purchase_sale_invoice WHERE invoice_date = :date")
    fun getAllEntries(date: LocalDate = LocalDate.now()): LiveData<List<PurchaseSale>>

    @Query("SELECT * FROM purchase_sale_invoice WHERE seller_id = :sellerId AND product_id = :productId AND invoice_date = :date")
    fun getItemsOfSeller(sellerId: Long, productId: Long, date: LocalDate = LocalDate.now()): LiveData<List<PurchaseSale>>
}