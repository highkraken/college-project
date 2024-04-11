package com.example.collegeproject.screens.admin.datainput.sale

import android.app.Activity
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.collegeproject.components.HeaderTextComp
import com.example.collegeproject.components.InvoiceItemDetailComp
import com.example.collegeproject.components.NameValueComp
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.utils.BillFormat
import com.example.collegeproject.utils.PdfConverter
import com.example.collegeproject.utils.PdfDetails
import com.example.collegeproject.viewmodels.admin.SaleDetailViewModel
import com.example.collegeproject.viewmodels.admin.SaleDetailViewModelFactory
import java.time.format.DateTimeFormatter

@Composable
fun SaleDetailScreen(
    modifier: Modifier = Modifier,
    purchaseSaleDao: PurchaseSaleDao?,
    userDao: UserDao,
    buyerId: Long,
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val saleDetailViewModel: SaleDetailViewModel = viewModel(
        factory = SaleDetailViewModelFactory(
            purchaseSaleDao!!,
            userDao!!,
            buyerId
        )
    )
    
    val products by saleDetailViewModel.products.observeAsState()

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
                    text = saleDetailViewModel.buyerName,
                    modifier = Modifier.weight(3f)
                )
                Text(
                    text = saleDetailViewModel.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
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
                        NameValueComp(name = "Labour", value = saleDetailViewModel.labour.toString())
                        NameValueComp(name = "Extra Expense", value = saleDetailViewModel.extraExpense.toString())
                        NameValueComp(name = "Total", value = saleDetailViewModel.totalAfterExpenses.toString())
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        NameValueComp(name = "Amount", value = saleDetailViewModel.total.toString())
                        NameValueComp(name = "All Expenses", value = saleDetailViewModel.totalAfterExpenses.toString())
                        NameValueComp(name = "Grand Total", value = saleDetailViewModel.grandTotal.toString())
                    }
                }
                Button(onClick = {
                    val pdfConverter = PdfConverter()
                    val pdfDetails = PdfDetails(
                        customerName = saleDetailViewModel.buyerName,
                        date = saleDetailViewModel.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        sItemsList = products!!,
                        grandTotal = saleDetailViewModel.grandTotal.toString(),
                        wage = saleDetailViewModel.labour.toString(),
                        total = saleDetailViewModel.total.toString()
                    )
                    pdfConverter.createPdf(
                        context,
                        pdfDetails,
                        activity,
                        BillFormat.SALE
                    )
                }) {
                    Text(text = "Save PDF")
                }
            }
        }
    }
}