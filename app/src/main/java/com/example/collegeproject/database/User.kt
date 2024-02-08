package com.example.collegeproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_table", indices = [Index(value = ["email_id"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("user_id")
    val userId: Long = 0L,

    @ColumnInfo(name = "business_name")
    val businessName: String,

    @ColumnInfo(name = "business_address")
    val businessAddress: String = "",

    @ColumnInfo(name = "owner_name")
    val ownerName: String = "",

    @ColumnInfo(name = "email_id")
    val emailId: String,

    @ColumnInfo(name = "phone")
    val phoneNumber: String = "",

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "user_type")
    val userType: String,
)
