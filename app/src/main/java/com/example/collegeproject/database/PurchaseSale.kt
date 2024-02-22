package com.example.collegeproject.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "purchase_sell_invoice",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["seller_id"]
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["buyer_id"]
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["product_id"],
            childColumns = ["product_id"]
        )
    ]
)
data class PurchaseSale(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("invoice_id")
    val invoiceId: Long = 0L,

    @ColumnInfo("invoice_date")
    val invoiceDate: LocalDate,

    @ColumnInfo("seller_id")
    val sellerId: Long,

    @ColumnInfo("seller_name")
    val sellerName: String,

    @ColumnInfo("buyer_id")
    val buyerId: Long,

    @ColumnInfo("buyer_name")
    val buyerName: String,

    @ColumnInfo("product_unit")
    val productUnit: Float,

    @Embedded
    val product: Product,

    @ColumnInfo("product_price")
    val productPrice: Float,

    @ColumnInfo("product_quantity")
    val productQuantity: Float,

    @ColumnInfo("product_total")
    val productTotal: Float
)
