package com.example.collegeproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.collegeproject.utils.DateConverter

@Database(entities = [User::class, Product::class, PurchaseSale::class], version = 3, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class MasterDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val productDao: ProductDao
    abstract val purchaseSaleDao: PurchaseSaleDao

    companion object {
        @Volatile
        private var INSTANCE: MasterDatabase? = null

        fun getInstance(context: Context): MasterDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MasterDatabase::class.java,
                        "user_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}