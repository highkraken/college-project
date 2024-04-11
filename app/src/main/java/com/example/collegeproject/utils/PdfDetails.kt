package com.example.collegeproject.utils

import com.example.collegeproject.model.PurchaseProductResult
import com.example.collegeproject.model.SaleProductResult

data class PdfDetails(
    val companyName: String = "",
    val slogan: String = "",
    val address: String = "",
    val customerName: String,
    val date: String,
    val sItemsList: List<SaleProductResult> = listOf(),
    val pItemsList: List<PurchaseProductResult> = listOf(),
    val wage: String,
    val total: String,
    val grandTotal: String,
    val commission: String = "",
    val importFare: String = "",
    val extExp: String = "",
    val allExtExp: String = ""
)
