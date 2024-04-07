package com.example.collegeproject.screens.admin.datainput.product

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.collegeproject.components.HeaderTextComp
import com.example.collegeproject.components.NormalTextComp
import com.example.collegeproject.components.TextFieldComp
import com.example.collegeproject.components.TypeValueComp
import com.example.collegeproject.database.ProductDao
import com.example.collegeproject.viewmodels.admin.AddProductViewModel
import com.example.collegeproject.viewmodels.admin.AddProductViewModelFactory

@Composable
fun AddProductScreen(
    modifier: Modifier = Modifier,
    productId: Long,
    productDao: ProductDao?,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val focusRequest = LocalFocusManager.current
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val addProductViewModel: AddProductViewModel =
        viewModel(factory = AddProductViewModelFactory(productDao!!, productId))
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { focusRequest.clearFocus() }
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderTextComp(
            text = "Add Product Details",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        TextFieldComp(
            textInput = addProductViewModel.productName,
            labelText = "Product Name",
            onValueChange = addProductViewModel::onProductNameChange,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        NormalTextComp(
            text = "Sale Expense Details",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TypeValueComp(
            expanded = addProductViewModel.sWageTypeExpand,
            onDismissRequest = { addProductViewModel.onSaleWageDismiss() },
            onExpandedChange = { addProductViewModel.onSaleWageExpandedChange() },
            selectedOption = addProductViewModel.sWageType,
            textFieldValue = addProductViewModel.sWage,
            labelText = "Wage",
            onTypeChange = addProductViewModel::setSelectedSaleWageType,
            onValueChange = addProductViewModel::onSaleWageChange
        )
        TypeValueComp(
            expanded = addProductViewModel.sExtExpTypeExpand,
            onDismissRequest = { addProductViewModel.onSaleExtraExpenseDismiss() },
            onExpandedChange = { addProductViewModel.onSaleExtraExpenseExpandedChange() },
            selectedOption = addProductViewModel.sExtExpType,
            textFieldValue = addProductViewModel.sExtExp,
            labelText = "Extra Expense",
            onTypeChange = addProductViewModel::setSelectedSaleExtraExpenseType,
            onValueChange = addProductViewModel::onSaleExtraExpenseChange,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        NormalTextComp(
            text = "Purchase Expense Details",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TypeValueComp(
            expanded = addProductViewModel.commisionTypeExpand,
            onDismissRequest = { addProductViewModel.onCommissionDismiss() },
            onExpandedChange = { addProductViewModel.onCommissionExpandedChange() },
            selectedOption = addProductViewModel.pCommissionType,
            textFieldValue = addProductViewModel.pCommission,
            labelText = "Commission",
            onTypeChange = addProductViewModel::setSelectedPurchaseCommissionType,
            onValueChange = addProductViewModel::onPurchaseCommissionChange
        )
        TypeValueComp(
            expanded = addProductViewModel.pWageTypeExpand,
            onDismissRequest = { addProductViewModel.onPurchaseWageDismiss() },
            onExpandedChange = { addProductViewModel.onPurchaseWageExpandedChange() },
            selectedOption = addProductViewModel.pWageType,
            textFieldValue = addProductViewModel.pWage,
            labelText = "Wage",
            onTypeChange = addProductViewModel::setSelectedPurchaseWageType,
            onValueChange = addProductViewModel::onPurchaseWageChange
        )
        TypeValueComp(
            expanded = addProductViewModel.pExtExpTypeExpand,
            onDismissRequest = { addProductViewModel.onPurchaseExtraExpenseDismiss() },
            onExpandedChange = { addProductViewModel.onPurchaseExtraExpenseExpandedChange() },
            selectedOption = addProductViewModel.pExtExpType,
            textFieldValue = addProductViewModel.pExtExp,
            labelText = "Extra Expense",
            onTypeChange = addProductViewModel::setSelectedPurchaseExtraExpenseType,
            onValueChange = addProductViewModel::onPurchaseExtraExpenseChange
        )
        TypeValueComp(
            expanded = addProductViewModel.importFareTypeExpand,
            onDismissRequest = { addProductViewModel.onImportFareDismiss() },
            onExpandedChange = { addProductViewModel.onImportFareExpandedChange() },
            selectedOption = addProductViewModel.importFareType,
            textFieldValue = addProductViewModel.importFare,
            labelText = "Extra Expense",
            onTypeChange = addProductViewModel::setSelectedImportFareType,
            onValueChange = addProductViewModel::onImportFareChange
        )
        Button(onClick = {
            addProductViewModel.onAddProductClick()
            Toast.makeText(context, "Product added successfully!", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = if (productId == 0L) "Add Product" else "Update Product")
        }
    }
}

@Preview
@Composable
fun ProductEntryPreview() {
    AddProductScreen(productDao = null, productId = 0L)
}