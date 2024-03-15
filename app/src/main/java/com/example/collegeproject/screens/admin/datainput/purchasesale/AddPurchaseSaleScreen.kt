package com.example.collegeproject.screens.admin.datainput.purchasesale

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.collegeproject.components.DropdownFieldComp
import com.example.collegeproject.components.PrimaryButtonComp
import com.example.collegeproject.components.TextFieldComp
import com.example.collegeproject.components.TypeValueComp
import com.example.collegeproject.database.ProductDao
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.utils.PriceType
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
    val focusRequest = LocalFocusManager.current
    val interactionSource = remember {
        MutableInteractionSource()
    }
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
    var allOtherProducts: List<String> = listOf()
    var buyers: List<String> = listOf()
    var sellers: List<String> = listOf()

    purchaseSaleViewModel.productsOfSeller.observe(viewLifecycleOwner) { (sProducts, nonSProducts) ->
        sellerProducts = sProducts
        allOtherProducts = nonSProducts
    }
    purchaseSaleViewModel.buyers.observe(viewLifecycleOwner) { vmBuyer ->
        buyers = vmBuyer.map { "${it.userId}. ${it.ownerName}" }
    }
    purchaseSaleViewModel.sellers.observe(viewLifecycleOwner) { vmSeller ->
        sellers = vmSeller.map { "${it.userId}. ${it.ownerName}" }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { focusRequest.clearFocus() }
    ) {
        DropdownFieldComp(
            expanded = purchaseSaleViewModel.sellerExpand,
            onDismissRequest = {
                purchaseSaleViewModel.onSellerDismiss()
                focusRequest.clearFocus()
            },
            onExpandedChange = { purchaseSaleViewModel.onSellerExpandedChange() },
            selectedOption = purchaseSaleViewModel.sellerName,
            labelText = "Seller",
            onTypeChange = purchaseSaleViewModel::setSelectedSeller,
            dropdownList = sellers,
            readOnly = false
        )
        DropdownFieldComp(
            expanded = purchaseSaleViewModel.productExpand,
            onDismissRequest = {
                purchaseSaleViewModel.onProductDismiss()
                focusRequest.clearFocus()
            },
            onExpandedChange = { purchaseSaleViewModel.onProductExpandedChange() },
            selectedOption = purchaseSaleViewModel.productName,
            labelText = "Product",
            onTypeChange = purchaseSaleViewModel::onProductNameChange,
            dropdownList = sellerProducts + allOtherProducts,
            readOnly = false
        )
        TypeValueComp(
            expanded = purchaseSaleViewModel.unitTypeExpand,
            onDismissRequest = { purchaseSaleViewModel.onUnitTypeDismiss() },
            onExpandedChange = { purchaseSaleViewModel.onUnitTypeExpandedChange() },
            selectedOption = purchaseSaleViewModel.unitType,
            textFieldValue = purchaseSaleViewModel.unit,
            labelText = "Unit",
            onTypeChange = purchaseSaleViewModel::setSelectedUnitType,
            onValueChange = purchaseSaleViewModel::onUnitChange
        )
        TextFieldComp(
            textInput = purchaseSaleViewModel.quantity,
            labelText = "Quantity",
            onValueChange = purchaseSaleViewModel::onQuantityChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        TypeValueComp(
            expanded = purchaseSaleViewModel.priceTypeExpand,
            onDismissRequest = { purchaseSaleViewModel.onPriceTypeDismiss() },
            onExpandedChange = { purchaseSaleViewModel.onPriceTypeExpandedChange() },
            selectedOption = purchaseSaleViewModel.priceType,
            textFieldValue = purchaseSaleViewModel.price,
            labelText = "Price",
            onTypeChange = purchaseSaleViewModel::setSelectedPriceType,
            onValueChange = purchaseSaleViewModel::onPriceChange,
            typeList = PriceType.entries.map { it.type }
        )
        DropdownFieldComp(
            expanded = purchaseSaleViewModel.buyerExpand,
            onDismissRequest = {
                purchaseSaleViewModel.onBuyerDismiss()
                focusRequest.clearFocus()
            },
            onExpandedChange = { purchaseSaleViewModel.onBuyerExpandedChange() },
            selectedOption = purchaseSaleViewModel.buyerName,
            labelText = "Buyer",
            onTypeChange = purchaseSaleViewModel::setSelectedBuyerName,
            dropdownList = buyers,
            readOnly = false
        )
        TextFieldComp(
            textInput = purchaseSaleViewModel.totalAmount,
            labelText = "Total Amount",
            onValueChange = purchaseSaleViewModel::onTotalAmountChange,
            readOnly = true
        )
        PrimaryButtonComp(text = "Save and Next") {
            purchaseSaleViewModel.onSaveAndNextClick()
        }
    }
}

@Preview
@Composable
fun AddPurchaseSalePreview() {
    AddPurchaseSaleScreen()
}