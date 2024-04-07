package com.example.collegeproject.screens.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AddBox
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
import com.example.collegeproject.navigation.AdminDataInputNavGraph
import com.example.collegeproject.screens.HomeScreen
import com.example.collegeproject.screens.admin.datainput.product.AddProductScreen
import com.example.collegeproject.screens.admin.datainput.purchase.PurchaseDetailScreen
import com.example.collegeproject.screens.admin.datainput.purchasesale.AddPurchaseSaleScreen
import com.example.collegeproject.screens.admin.datainput.sale.SaleDetailScreen
import com.example.collegeproject.utils.AdminBottomBarItem
import com.example.collegeproject.utils.AdminNavigation
import com.example.collegeproject.utils.StartupScreen
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
            label = "Product",
            selectedIcon = Icons.Filled.AddBox,
            unselectedIcon = Icons.Outlined.AddBox,
            route = "add_product"
        ),
        AdminBottomBarItem(
            label = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = AdminNavigation.DataInput.route
        ),
        AdminBottomBarItem(
            label = "Seller",
            selectedIcon = Icons.AutoMirrored.Filled.Login,
            unselectedIcon = Icons.AutoMirrored.Outlined.Login,
            route = StartupScreen.Login.route
        ),
        AdminBottomBarItem(
            label = "Buyer",
            selectedIcon = Icons.AutoMirrored.Filled.Assignment,
            unselectedIcon = Icons.AutoMirrored.Outlined.Assignment,
            route = StartupScreen.Admin.route
        ),
        AdminBottomBarItem(
            label = "Logout",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = StartupScreen.Home.route
        ),
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
            NavHost(navController = navController, startDestination = AdminNavigation.DataInput.route) {
                composable(StartupScreen.Admin.route) {
                    SaleDetailScreen(
                        buyerId = 8,
                        purchaseSaleDao = masterDatabase?.purchaseSaleDao!!,
                        userDao = masterDatabase.userDao
                    )
                }

                composable(AdminNavigation.DataInput.route) {
                    AdminDataInputNavGraph(
                        navController = rememberNavController(),
                        productDao = masterDatabase!!.productDao,
                        purchaseSaleDao = masterDatabase.purchaseSaleDao,
                        userDao = masterDatabase.userDao
                    )
                }

                composable(StartupScreen.Login.route) {
//                    HomeScreen(
//                        navController = navController,
//                        userDao = userDao,
//                        userPreferencesRepository = userPreferencesRepository,
//                        userType = "Seller"
//                    )

//                    ProductEntryScreen(productDao = masterDatabase?.productDao)

                    PurchaseDetailScreen(
                        sellerId = 3,
                        purchaseSaleDao = masterDatabase?.purchaseSaleDao!!,
                        userDao = masterDatabase.userDao
                    )
                }
                composable("add_product") {
                    AddProductScreen(
                        productDao = masterDatabase?.productDao,
                        productId = 0
                    )
                }
                composable(StartupScreen.Home.route) {
                    HomeScreen(
                        navController = navController,
                        userDao = userDao,
                        userPreferencesRepository = userPreferencesRepository,
                        userType = "Seller"
                    )
//                    LoginScreen(
//                        navController = null,
//                        userDao = userDao,
//                        userPreferencesRepository = userPreferencesRepository
//                    )
                }
                composable(StartupScreen.SignUp.route) {
                    AddPurchaseSaleScreen(
                        purchaseSaleDao = masterDatabase?.purchaseSaleDao,
                        userDao = masterDatabase?.userDao,
                        productDao = masterDatabase?.productDao,
                        sellerId = 3,
                        productId = 0
                    )
                }
            }
        }
    }
}