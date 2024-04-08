package com.example.collegeproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.collegeproject.database.ProductDao
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.screens.HomeScreen
import com.example.collegeproject.screens.InvoiceListScreen
import com.example.collegeproject.screens.UserListScreen
import com.example.collegeproject.utils.StartupScreen
import com.example.collegeproject.utils.UserNavigation
import com.example.collegeproject.utils.UserPreferencesRepository

@Composable
fun UserNavGraph(
    userDao: UserDao,
    purchaseSaleDao: PurchaseSaleDao,
    productDao: ProductDao,
    userPreferencesRepository: UserPreferencesRepository
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = StartupScreen.Home.route) {
        composable(
            route = StartupScreen.Home.route,
            arguments = listOf(navArgument("userType") {
                type = NavType.StringType
            })
        ) { entry ->
            val userType = entry.arguments?.getString("userType")
            if (userType != null) {
                HomeScreen(
                    navController = navController,
                    userDao = userDao,
                    userPreferencesRepository = userPreferencesRepository,
                    userType = userType
                )
            }
        }

        composable(
            route = UserNavigation.UserList.route,
            arguments = listOf(navArgument("userType") {
                type = NavType.StringType
            })
        ) { entry ->
            val userType = entry.arguments?.getString("userType")
            if (userType != null) {
                UserListScreen(
                    userDao = userDao,
                    purchaseSaleDao = purchaseSaleDao,
                    userType = userType
                )
            }
        }

        navigation(route = UserNavigation.Invoice.route, startDestination = UserNavigation.Invoice.InvoiceList.route) {
            composable(
                route = UserNavigation.Invoice.InvoiceList.route,
                arguments = listOf(navArgument("userType") {
                    type = NavType.StringType
                })
            ) { entry ->
                val userType = entry.arguments?.getString("userType")
                if (userType != null) {
                    InvoiceListScreen(
                        purchaseSaleDao = purchaseSaleDao,
                        userType = userType,
                        userPreferencesRepository = userPreferencesRepository
                    )
                }
            }
//            composable(
//                route = UserNavigation.Invoice.InvoiceDetail.route,
//                arguments = listOf(navArgument())
//            )
        }
    }
}