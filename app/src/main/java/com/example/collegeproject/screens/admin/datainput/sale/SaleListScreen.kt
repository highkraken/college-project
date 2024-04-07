package com.example.collegeproject.screens.admin.datainput.sale

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
import com.example.collegeproject.viewmodels.admin.SaleListViewModel
import com.example.collegeproject.viewmodels.admin.SaleListViewModelFactory

@Composable
fun SaleListScreen(
    modifier: Modifier = Modifier,
    purchaseSaleDao: PurchaseSaleDao,
    navController: NavHostController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val saleListViewModel: SaleListViewModel = viewModel(factory = SaleListViewModelFactory(purchaseSaleDao))

    var saleList = listOf<PurchaseSale>()

    saleListViewModel.saleList.observe(lifecycleOwner) { list ->
        if (list != null) {
            saleList = list
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(saleList) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(
                            AdminNavigation.Sale.SaleDetail.route
                                .replace("{buyerId}", item.buyerId.toString())
                        )
                    }
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${item.buyerId}. ${item.buyerName}")
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = ""
                )
            }
            Divider()
        }
    }
}