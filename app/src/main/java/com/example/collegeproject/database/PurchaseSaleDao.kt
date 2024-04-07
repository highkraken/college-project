package com.example.collegeproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.collegeproject.model.PurchaseProductResult
import com.example.collegeproject.model.SellerProductListModel
import com.example.collegeproject.model.SaleProductResult
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

    @Query("SELECT product_id AS productId, SUM(product_unit) AS productUnit, product_name AS productName, SUM(product_quantity) AS productQuantity, " +
            "product_price AS productPrice, SUM(product_total) AS productTotal, s_wage AS labour, s_wage_type AS labourType, " +
            "s_extra_expense AS extraExpense, s_extra_expense_type AS extraExpenseType FROM purchase_sale_invoice " +
            "WHERE buyer_id = :buyerId AND invoice_date = :date " +
            "GROUP BY product_id, product_price " +
            "ORDER BY product_price DESC, product_name ASC")
    fun getProductsOfBuyer(buyerId: Long, date: LocalDate = LocalDate.now()): LiveData<List<SaleProductResult>>

    @Query("SELECT product_id AS productId, product_name AS productName, seller_id AS sellerId, seller_name AS sellerName " +
            "FROM purchase_sale_invoice WHERE invoice_date = :date GROUP BY sellerId, productId")
    fun getSellerProductList(date: LocalDate = LocalDate.now()): LiveData<List<SellerProductListModel>>

    @Query("SELECT * FROM purchase_sale_invoice WHERE invoice_id = :purchaseSaleId AND invoice_date = :date")
    fun getEntryById(purchaseSaleId: Long, date: LocalDate = LocalDate.now()): PurchaseSale

    @Query("SELECT * FROM purchase_sale_invoice WHERE seller_id = :sellerId AND product_id = :productId AND invoice_date = :date")
    fun getPurchaseSaleList(sellerId: Long, productId: Long, date: LocalDate = LocalDate.now()): LiveData<List<PurchaseSale>>

    @Query("SELECT * FROM purchase_sale_invoice WHERE invoice_date = :date GROUP BY seller_id ORDER BY seller_id")
    fun getPurchaseList(date: LocalDate = LocalDate.now()): LiveData<List<PurchaseSale>>

    @Query("SELECT * FROM purchase_sale_invoice WHERE invoice_date = :date GROUP BY buyer_id ORDER BY buyer_id")
    fun getSaleList(date: LocalDate = LocalDate.now()): LiveData<List<PurchaseSale>>
}