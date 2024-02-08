package com.example.collegeproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDatabaseDao {
    @Upsert
    fun upsertUser(user: User)

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE email_id LIKE :email AND password LIKE :password")
    fun getUserByCredentials(email: String, password: String): User?

    @Query("SELECT * FROM user_table WHERE email_id LIKE :email")
    fun getUserByEmail(email: String): User?
}