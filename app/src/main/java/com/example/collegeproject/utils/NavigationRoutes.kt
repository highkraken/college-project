package com.example.collegeproject.utils

sealed class StartupScreen(val route: String) {
    data object SignUp : StartupScreen("sign_up")
    data object Login : StartupScreen("login")
    data object Home : StartupScreen("home/{userType}")
    data object Splash : StartupScreen("splash")
    data object Admin : StartupScreen("admin")
}

sealed class AdminNavigation(val route: String) {
    data object DataInput : AdminNavigation("data_input")
    data object AddProduct : AdminNavigation("add_product")
    data object ProductList : AdminNavigation("product_list")
    data object ProductDetail : AdminNavigation("product_detail")
    data object AddPurchaseSale : AdminNavigation("add_purchase_sale")
    data object PurchaseSaleList : AdminNavigation("purchase_sale_list")
    data object PurchaseSaleDetail : AdminNavigation("purchase_sale_detail")
    data object AddPurchase : AdminNavigation("add_purchase")
    data object PurchaseList : AdminNavigation("purchase_list")
    data object PurchaseDetail : AdminNavigation("purchase_detail")
    data object AddSale : AdminNavigation("add_sale")
    data object SaleList : AdminNavigation("sale_list")
    data object SaleDetail : AdminNavigation("sale_detail")
    data object AddCredit : AdminNavigation("add_credit")
    data object CreditList : AdminNavigation("credit_list")
    data object CreditDetail : AdminNavigation("credit_detail")
}