package com.example.collegeproject.screens.admin.datainput.purchasesale

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.collegeproject.components.NormalTextComp
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.model.SellerProductListModel
import com.example.collegeproject.utils.AdminNavigation
import com.example.collegeproject.viewmodels.admin.SellerProductListViewModel
import com.example.collegeproject.viewmodels.admin.SellerProductViewModelFactory

@Composable
fun SellerProductListScreen(
    modifier: Modifier = Modifier,
    purchaseSaleDao: PurchaseSaleDao,
    navController: NavHostController
) {
    val sellerProductListViewModel: SellerProductListViewModel =
        viewModel(factory = SellerProductViewModelFactory(purchaseSaleDao))

    val viewLifecycleOwner = LocalLifecycleOwner.current

    var sellerProductList = listOf<SellerProductListModel>()

    sellerProductListViewModel.sellerProductList.observe(viewLifecycleOwner) { vmList ->
        if (vmList != null) {
            sellerProductList = vmList
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(AdminNavigation.PurchaseSale.PurchaseSaleDetail.route)
            }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add entry")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(sellerProductList) { sellerProductName ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            navController
                                .navigate(
                                    AdminNavigation.PurchaseSale.PurchaseSaleList.route
                                        .replace(
                                            oldValue = "{productId}",
                                            newValue = sellerProductName.productId.toString()
                                        )
                                        .replace(
                                            oldValue = "{sellerId}",
                                            newValue = sellerProductName.sellerId.toString()
                                        )
                                )
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalTextComp(
                        text = sellerProductName.sellerName,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = sellerProductName.productName, modifier = Modifier.weight(1f))
                }
                Divider()
            }
        }
    }
}