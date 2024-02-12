package com.example.collegeproject.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

private object PreferencesKeys {
    val USER_ID = longPreferencesKey("user_id")
    val BUSINESS_NAME = stringPreferencesKey("business_name")
    val BUSINESS_ADDRESS = stringPreferencesKey("business_address")
    val OWNER_NAME = stringPreferencesKey("owner_name")
    val EMAIL = stringPreferencesKey("email_id")
    val PHONE = stringPreferencesKey("phone")
//    val PASSWORD = stringPreferencesKey("password")
    val USER_TYPE = stringPreferencesKey("user_type")
}

data class UserPreferences(
    val userId: Long = 0L,
    val businessName: String = "",
    val businessAddress: String = "",
    val ownerName: String = "",
    val email: String = "",
    val phone: String = "",
//    val password: String,
    val userType: String = ""
)

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val userId = preferences[PreferencesKeys.USER_ID] ?: 0L
            val businessName = preferences[PreferencesKeys.BUSINESS_NAME] ?: ""
            val businessAddress = preferences[PreferencesKeys.BUSINESS_ADDRESS] ?: ""
            val ownerName = preferences[PreferencesKeys.OWNER_NAME] ?: ""
            val email = preferences[PreferencesKeys.EMAIL] ?: ""
            val phone = preferences[PreferencesKeys.PHONE] ?: ""
            val userType = preferences[PreferencesKeys.USER_TYPE] ?: ""
            UserPreferences(userId, businessName, businessAddress, ownerName, email, phone, userType)
        }

    suspend fun updateUserPreferences(userPreferences: UserPreferences) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = userPreferences.userId
            preferences[PreferencesKeys.BUSINESS_NAME] = userPreferences.businessName
            preferences[PreferencesKeys.BUSINESS_ADDRESS] = userPreferences.businessAddress
            preferences[PreferencesKeys.OWNER_NAME] = userPreferences.ownerName
            preferences[PreferencesKeys.EMAIL] = userPreferences.email
            preferences[PreferencesKeys.PHONE] = userPreferences.phone
            preferences[PreferencesKeys.USER_TYPE] = userPreferences.userType
        }
    }

    suspend fun loggedInAs(): String? {
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.USER_TYPE]
    }
}

//class PreferencesManager(context: Context) {
//    private val sharedPreferences: SharedPreferences =
//        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//
//    fun saveData(key: String, value: String) {
//        val editor = sharedPreferences.edit()
//        editor.putString(key, value)
//        editor.apply()
//    }
//
//    fun getData(key: String, defaultValue: String): String {
//        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
//    }
//}