package com.example.collegeproject.utils

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import java.time.LocalDate

@SuppressLint("NewApi")
class DateConverter {
    @TypeConverter
    fun fromTimeStamp(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun toTimeStamp(date: LocalDate?): String? {
        return date?.toString()
    }
}