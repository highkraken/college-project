package com.example.collegeproject.screens.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collegeproject.components.AdminBottomBar
import com.example.collegeproject.database.MasterDatabase
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.screens.HomeScreen
import com.example.collegeproject.screens.LoginScreen
import com.example.collegeproject.screens.admin.datainput.DataInputScreen
import com.example.collegeproject.screens.admin.datainput.ProductEntryScreen
import com.example.collegeproject.utils.AdminBottomBarItem
import com.example.collegeproject.utils.Screen
import com.example.collegeproject.utils.UserPreferencesRepository

@Composable
fun AdminScreen(
    modifier: Modifier = Modifier,
//    navController: NavController?,
    masterDatabase: MasterDatabase?,
    userPreferencesRepository: UserPreferencesRepository,
    userDao: UserDao,
) {
    val screens = listOf(
        AdminBottomBarItem(
            label = "Data Input",
            selectedIcon = Icons.AutoMirrored.Filled.Assignment,
            unselectedIcon = Icons.AutoMirrored.Outlined.Assignment,
            route = Screen.SignUp.route
        ),
        AdminBottomBarItem(
            label = "Login",
            selectedIcon = Icons.AutoMirrored.Filled.Login,
            unselectedIcon = Icons.AutoMirrored.Outlined.Login,
            route = Screen.Login.route
        ),
        AdminBottomBarItem(
            label = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = Screen.Home.route
        )
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            AdminBottomBar(navController = navController, screens = screens)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(navController = navController, startDestination = Screen.SignUp.route) {
                composable(Screen.Home.route) {
//                    HomeScreen(
//                        navController = navController,
//                        userDao = userDao,
//                        userPreferencesRepository = userPreferencesRepository,
//                        userType = "Seller"
//                    )
                    ProductEntryScreen(productDao = masterDatabase?.productDao)
                }
                composable(Screen.Login.route) {
                    LoginScreen(
                        navController = null,
                        userDao = userDao,
                        userPreferencesRepository = userPreferencesRepository
                    )
                }
                composable(Screen.SignUp.route) {
                    DataInputScreen(
//                        navController = null,
//                        userDao = userDao,
//                        userPreferencesRepository = userPreferencesRepository
                    )
                }
            }
        }
    }
}