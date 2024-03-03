package com.example.collegeproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ProductDao {
    @Upsert
    fun upsertProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table WHERE product_id = :productId")
    fun getProductById(productId: Long): Product

    @Query("SELECT product_id FROM product_table WHERE product_name = :productName")
    fun getProductIdByName(productName: String): Long

    @Query("SELECT pr.* FROM product_table pr " +
            "LEFT JOIN purchase_sale_invoice ps " +
            "ON pr.product_id = ps.product_id " +
            "WHERE ps.seller_id = :sellerId")
    fun getProductsOfSeller(sellerId: Long): LiveData<List<Product>>

    @Query("SELECT pr.* FROM product_table pr " +
            "LEFT JOIN purchase_sale_invoice ps " +
            "ON pr.product_id = ps.product_id " +
            "WHERE ps.seller_id IS NOT :sellerId")
    fun getProductsNotOfSeller(sellerId: Long): LiveData<List<Product>>
}