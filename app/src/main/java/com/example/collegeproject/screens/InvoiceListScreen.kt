package com.example.collegeproject.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.collegeproject.database.PurchaseSale
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.utils.UserPreferences
import com.example.collegeproject.utils.UserPreferencesRepository
import com.example.collegeproject.viewmodels.InvoiceListViewModel
import com.example.collegeproject.viewmodels.InvoiceListViewModelFactory

@Composable
fun InvoiceListScreen(
    modifier: Modifier = Modifier,
    purchaseSaleDao: PurchaseSaleDao,
    userType: String,
    userPreferencesRepository: UserPreferencesRepository
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val userId = userPreferencesRepository.userPreferencesFlow.collectAsState(UserPreferences()).value.userId
    val invoiceListViewModel: InvoiceListViewModel = viewModel(factory = InvoiceListViewModelFactory(purchaseSaleDao, userType, userId))
    var invoiceList = listOf<PurchaseSale>()

    invoiceListViewModel.invoiceList.observe(lifecycleOwner) { list ->
        if (list != null) {
            invoiceList = list
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(invoiceList) { invoice ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(text = invoice.invoiceDate.toString())
            }
        }
    }
}