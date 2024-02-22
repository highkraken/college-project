package com.example.collegeproject

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.preferencesDataStore
import com.example.collegeproject.app.BillOrganizerApp
import com.example.collegeproject.database.MasterDatabase
import com.example.collegeproject.ui.theme.CollegeProjectTheme
import com.example.collegeproject.utils.Constants
import com.example.collegeproject.utils.UserPreferencesRepository

class MainActivity : ComponentActivity() {
    private val Context.dataStore by preferencesDataStore(
        name = Constants.USER_PREFERENCE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollegeProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val masterDatabase = MasterDatabase.getInstance(applicationContext)
                    val userPreferencesRepository = UserPreferencesRepository(dataStore)
                    BillOrganizerApp(masterDatabase.userDao, userPreferencesRepository)
                }
            }
        }
    }
}