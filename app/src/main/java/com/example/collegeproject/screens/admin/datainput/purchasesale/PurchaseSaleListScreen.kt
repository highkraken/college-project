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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.collegeproject.components.HeaderTextComp
import com.example.collegeproject.components.NormalTextComp
import com.example.collegeproject.database.PurchaseSale
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.utils.AdminNavigation
import com.example.collegeproject.viewmodels.admin.PurchaseSaleListViewModel
import com.example.collegeproject.viewmodels.admin.PurchaseSaleListViewModelFactory
import java.time.LocalDate

@Composable
fun PurchaseSaleListScreen(
    modifier: Modifier = Modifier,
    purchaseSaleDao: PurchaseSaleDao,
    navController: NavHostController,
    sellerId: Long,
    productId: Long
) {
    val purchaseSaleListViewModel: PurchaseSaleListViewModel = viewModel(factory = PurchaseSaleListViewModelFactory(
        purchaseSaleDao, sellerId, productId, LocalDate.now()
    ))

    val viewLifecycleOwner = LocalLifecycleOwner.current

    var purchaseSaleList = listOf<PurchaseSale>()

    purchaseSaleListViewModel.purchaseSaleList.observe(viewLifecycleOwner) { list ->
        if (list != null) {
            purchaseSaleList = list
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            if (purchaseSaleList.isNotEmpty()) {
                HeaderTextComp(text = purchaseSaleList.first().sellerName)
                NormalTextComp(text = purchaseSaleList.first().product.productName)
            }
        }
        items(purchaseSaleList) { purchaseSaleItem ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        navController
                            .navigate(
                                AdminNavigation.PurchaseSale.PurchaseSaleDetail.route
                                    .replace(
                                        "{purchaseSaleId}",
                                        purchaseSaleItem.invoiceId.toString()
                                    )
                            )
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                NormalTextComp(text = purchaseSaleItem.buyerName, modifier = Modifier.weight(1f))
            }
            Divider()
        }
    }
}