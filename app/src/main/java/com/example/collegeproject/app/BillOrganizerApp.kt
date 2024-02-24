package com.example.collegeproject.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.screens.LoginScreen
import com.example.collegeproject.screens.SignUpScreen
import com.example.collegeproject.utils.StartupScreen
import androidx.navigation.NavBackStackEntry
import com.example.collegeproject.database.MasterDatabase
import com.example.collegeproject.screens.admin.AdminScreen
import com.example.collegeproject.screens.HomeScreen
import com.example.collegeproject.screens.SplashScreen
import com.example.collegeproject.utils.UserPreferencesRepository

@Composable
fun BillOrganizerApp(
    userDao: UserDao,
    userPreferencesRepository: UserPreferencesRepository,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = StartupScreen.Splash.route) {
            composable(route = StartupScreen.Splash.route) {
                SplashScreen(
                    userPreferencesRepository = userPreferencesRepository,
                    navController = navController
                )
            }
            composable(route = StartupScreen.Login.route) {
                LoginScreen(
                    navController = navController,
                    userDao = userDao,
                    userPreferencesRepository = userPreferencesRepository
                )
            }
            composable(route = StartupScreen.SignUp.route) {
                SignUpScreen(
                    navController = navController,
                    userDao = userDao,
                    userPreferencesRepository = userPreferencesRepository
                )
            }
            composable(route = StartupScreen.Home.route) { backStackEntry: NavBackStackEntry ->
                val userType =
                    if (backStackEntry.arguments?.getString("userType")!! == "Seller") "Buyers" else "Sellers"
                HomeScreen(
                    navController = navController,
                    userDao = userDao,
                    userPreferencesRepository = userPreferencesRepository,
                    userType = userType
                )
            }
            composable(route = StartupScreen.Admin.route) {
                AdminScreen(
                    masterDatabase = MasterDatabase.getInstance(
                        LocalContext.current
                    ),
                    userDao = userDao,
                    userPreferencesRepository = userPreferencesRepository
                )
            }
        }
    }
}