package com.example.collegeproject.screens.admin.datainput.purchase

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.collegeproject.database.PurchaseSale
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.utils.AdminNavigation
import com.example.collegeproject.viewmodels.admin.PurchaseListViewModel
import com.example.collegeproject.viewmodels.admin.PurchaseListViewModelFactory

@Composable
fun PurchaseListScreen(
    modifier: Modifier = Modifier,
    purchaseSaleDao: PurchaseSaleDao,
    navController: NavHostController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val purchaseListViewModel: PurchaseListViewModel = viewModel(factory = PurchaseListViewModelFactory(purchaseSaleDao))

    var purchaseList = listOf<PurchaseSale>()

    purchaseListViewModel.purchaseList.observe(lifecycleOwner) { list ->
        if (list != null) {
            purchaseList = list
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(purchaseList) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(
                            AdminNavigation.Purchase.PurchaseDetail.route
                                .replace("{sellerId}", item.sellerId.toString())
                        )
                    }
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${item.sellerId}. ${item.sellerName}")
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = ""
                )
            }
            Divider()
        }
    }
}