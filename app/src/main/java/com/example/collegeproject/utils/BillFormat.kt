package com.example.collegeproject.utils

import com.example.collegeproject.R

enum class BillFormat(val formatId: Int) {
    PURCHASE(R.layout.purchase_bill_layout),
    SALE(R.layout.sale_bill_format)
}