package com.example.collegeproject.screens.admin.datainput.purchasesale

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.collegeproject.components.DropdownFieldComp
import com.example.collegeproject.components.TextFieldComp
import com.example.collegeproject.database.ProductDao
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.viewmodels.admin.AddPurchaseSaleViewModel
import com.example.collegeproject.viewmodels.admin.AddPurchaseSaleViewModelFactory

@Composable
fun AddPurchaseSaleScreen(
    modifier: Modifier = Modifier,
    purchaseSaleDao: PurchaseSaleDao? = null,
    userDao: UserDao? = null,
    productDao: ProductDao? = null,
    sellerId: Long = 0L,
    productId: Long = 0L,
) {
    val context = LocalContext.current
    val viewLifecycleOwner = LocalLifecycleOwner.current
    val purchaseSaleViewModel: AddPurchaseSaleViewModel = viewModel(
        factory = AddPurchaseSaleViewModelFactory(
            purchaseSaleDao!!,
            userDao!!,
            productDao!!,
            sellerId,
            productId
        )
    )
    var sellerProducts: List<String> = listOf()
    var nonSellerProducts: List<String> = listOf()
    purchaseSaleViewModel.productsOfSeller.observe(viewLifecycleOwner) { (sProducts, nonSProducts) ->
        if (sProducts.isNotEmpty() && nonSProducts.isNotEmpty()) {
            sellerProducts = sProducts
            nonSellerProducts = nonSProducts
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DropdownFieldComp(
            expanded = purchaseSaleViewModel.sellerExpand,
            onDismissRequest = { purchaseSaleViewModel.onSellerDismiss() },
            onExpandedChange = { purchaseSaleViewModel.onSellerExpandedChange() },
            selectedOption = purchaseSaleViewModel.sellerName,
            labelText = "Seller",
            onTypeChange = purchaseSaleViewModel::setSelectedSeller,
            dropdownList = purchaseSaleViewModel.getSellers(),
            readOnly = false
        )
        DropdownFieldComp(
            expanded = purchaseSaleViewModel.productExpand,
            onDismissRequest = { purchaseSaleViewModel.onProductDismiss() },
            onExpandedChange = { purchaseSaleViewModel.onProductExpandedChange() },
            selectedOption = purchaseSaleViewModel.productName,
            labelText = "Product",
            onTypeChange = purchaseSaleViewModel::setSelectedProduct,
            dropdownList = sellerProducts + nonSellerProducts,
            readOnly = false
        )
    }
}

@Preview
@Composable
fun AddPurchaseSalePreview() {
    AddPurchaseSaleScreen()
}