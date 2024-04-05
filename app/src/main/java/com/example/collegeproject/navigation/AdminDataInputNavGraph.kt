package com.example.collegeproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.collegeproject.database.ProductDao
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.screens.admin.datainput.DataInputScreen
import com.example.collegeproject.screens.admin.datainput.purchasesale.AddPurchaseSaleScreen
import com.example.collegeproject.screens.admin.datainput.purchasesale.PurchaseSaleListScreen
import com.example.collegeproject.screens.admin.datainput.purchasesale.SellerProductListScreen
import com.example.collegeproject.utils.AdminNavigation

@Composable
fun AdminDataInputNavGraph(
    navController: NavHostController,
    productDao: ProductDao,
    purchaseSaleDao: PurchaseSaleDao,
    userDao: UserDao,
) {
    NavHost(navController = navController, startDestination = AdminNavigation.DataInput.route) {
        composable(AdminNavigation.DataInput.route) {
            DataInputScreen(navController)
        }

        navigation(startDestination = AdminNavigation.PurchaseSale.SellerProductList.route, route = AdminNavigation.PurchaseSale.route) {
            composable(AdminNavigation.PurchaseSale.SellerProductList.route) {
                SellerProductListScreen(purchaseSaleDao = purchaseSaleDao, navController = navController)
            }

            composable(AdminNavigation.PurchaseSale.AddPurchaseSale.route) {
                AddPurchaseSaleScreen(purchaseSaleDao = purchaseSaleDao, userDao = userDao, productDao = productDao)
            }

            composable(
                route = AdminNavigation.PurchaseSale.PurchaseSaleList.route,
                arguments = listOf(navArgument("productId") { type = NavType.LongType }, navArgument("sellerId") { type = NavType.LongType })
            ) { entry ->
                val sellerId = entry.arguments?.getLong("sellerId")
                val productId = entry.arguments?.getLong("productId")
                if (sellerId != null && productId != null) {
                    PurchaseSaleListScreen(
                        purchaseSaleDao = purchaseSaleDao,
                        navController = navController,
                        sellerId = sellerId,
                        productId = productId
                    )
                }
            }

            composable(
                route = AdminNavigation.PurchaseSale.PurchaseSaleDetail.route,
                arguments = listOf(navArgument("purchaseSaleId") { type = NavType.LongType })
            ) { entry ->
                val purchaseSaleId = entry.arguments?.getLong("purchaseSaleId")
                if (purchaseSaleId != null) {
                    AddPurchaseSaleScreen(purchaseSaleDao = purchaseSaleDao, productDao = productDao, userDao = userDao, purchaseSaleId = purchaseSaleId)
                }
            }
        }
    }
}