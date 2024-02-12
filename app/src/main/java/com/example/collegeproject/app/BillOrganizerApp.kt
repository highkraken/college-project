package com.example.collegeproject.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collegeproject.database.UserDatabaseDao
import com.example.collegeproject.screens.LoginScreen
import com.example.collegeproject.screens.SignUpScreen
import com.example.collegeproject.utils.Screen
import androidx.datastore.preferences.core.Preferences
import com.example.collegeproject.screens.HomeScreen
import com.example.collegeproject.screens.SplashScreen
import com.example.collegeproject.utils.UserPreferencesRepository

@Composable
fun BillOrganizerApp(
    userDatabaseDao: UserDatabaseDao,
    userPreferencesRepository: UserPreferencesRepository
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        
        NavHost(navController = navController, startDestination = Screen.Splash.route) {
            composable(route = Screen.Splash.route) {
                SplashScreen(userPreferencesRepository = userPreferencesRepository, navController = navController)
            }
            composable(route = Screen.Login.route) {
                LoginScreen(navController = navController, userDatabaseDao = userDatabaseDao, userPreferencesRepository = userPreferencesRepository)
            }
            composable(route = Screen.SignUp.route) {
                SignUpScreen(navController = navController, userDatabaseDao = userDatabaseDao, userPreferencesRepository = userPreferencesRepository)
            }
            composable(route = Screen.Home.route) {
                HomeScreen(navController = navController, userPreferencesRepository = userPreferencesRepository)
            }
        }
    }
}