package com.example.collegeproject.screens.admin.datainput.purchase

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.collegeproject.components.HeaderTextComp
import com.example.collegeproject.components.InvoiceItemDetailComp
import com.example.collegeproject.components.NameValueComp
import com.example.collegeproject.components.NormalTextComp
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.model.PurchaseProductResult
import com.example.collegeproject.viewmodels.admin.PurchaseDetailViewModel
import com.example.collegeproject.viewmodels.admin.PurchaseDetailViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun PurchaseDetailScreen(
    modifier: Modifier = Modifier,
    purchaseSaleDao: PurchaseSaleDao,
    userDao: UserDao,
    sellerId: Long,
) {
    val purchaseDetailViewModel: PurchaseDetailViewModel =
        viewModel(factory = PurchaseDetailViewModelFactory(purchaseSaleDao!!, userDao!!, sellerId))

    val products by purchaseDetailViewModel.products.observeAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeaderTextComp(
                    text = purchaseDetailViewModel.sellerName,
                    modifier = Modifier.weight(3f)
                )
                Text(
                    text = purchaseDetailViewModel.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            InvoiceItemDetailComp(isTitle = true)
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (products != null) {
            items(products!!) { product ->
                InvoiceItemDetailComp(
                    productUnit = product.productUnit.toString(),
                    productName = product.productName,
                    productQuantity = product.productQuantity.toString(),
                    productPrice = product.productPrice.toString(),
                    productTotal = product.productTotal.toString()
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        NameValueComp(name = "Commission", value = purchaseDetailViewModel.commission.toString())
                        NameValueComp(name = "Labour", value = purchaseDetailViewModel.labour.toString())
                        NameValueComp(name = "Import Fare", value = purchaseDetailViewModel.importFare.toString())
                        NameValueComp(name = "Extra Expense", value = purchaseDetailViewModel.extraExpense.toString())
                        NameValueComp(name = "Total", value = purchaseDetailViewModel.totalAfterExpenses.toString())
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        NameValueComp(name = "Amount", value = purchaseDetailViewModel.total.toString())
                        NameValueComp(name = "All Expenses", value = purchaseDetailViewModel.totalAfterExpenses.toString())
                        NameValueComp(name = "Grand Total", value = purchaseDetailViewModel.grandTotal.toString())
                    }
                }
            }
        }
    }
}