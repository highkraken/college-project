package com.example.collegeproject.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collegeproject.database.UserDatabaseDao
import com.example.collegeproject.screens.LoginScreen
import com.example.collegeproject.screens.SignUpScreen
import com.example.collegeproject.utils.Screen

@Composable
fun BillOrganizerApp(
    userDatabaseDao: UserDatabaseDao
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        
        NavHost(navController = navController, startDestination = Screen.Login.route) {
            composable(route = Screen.Login.route) {
                LoginScreen(navController = navController, userDatabaseDao = userDatabaseDao)
            }
            composable(route = Screen.SignUp.route) {
                SignUpScreen(navController = navController, userDatabaseDao = userDatabaseDao)
            }
        }
    }
}