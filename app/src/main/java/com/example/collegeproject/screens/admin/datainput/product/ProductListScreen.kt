package com.example.collegeproject.screens.admin.datainput.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.collegeproject.database.Product
import com.example.collegeproject.database.ProductDao
import com.example.collegeproject.utils.AdminNavigation
import com.example.collegeproject.viewmodels.admin.ProductListViewModel
import com.example.collegeproject.viewmodels.admin.ProductListViewModelFactory

@Composable
fun ProductListScreen(
    productDao: ProductDao,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewLifecycleOwner = LocalLifecycleOwner.current

    val productListViewModel: ProductListViewModel =
        viewModel(factory = ProductListViewModelFactory(productDao))

    var productList = listOf<Product>()

    productListViewModel.productList.observe(viewLifecycleOwner) { list ->
        if (list != null) {
            productList = list
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(AdminNavigation.Product.ProductDetail.route)
            }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(items = productList) { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(
                                AdminNavigation.Product.ProductDetail.route
                                    .replace("{productId}", product.productId.toString())
                            )
                        }
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${product.productId}. ${product.productName}")
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                        contentDescription = ""
                    )
                }
                Divider()
            }
        }
    }
}