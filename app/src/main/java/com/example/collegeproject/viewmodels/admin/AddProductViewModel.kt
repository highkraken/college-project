package com.example.collegeproject.viewmodels.admin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.collegeproject.database.Product
import com.example.collegeproject.database.ProductDao
import com.example.collegeproject.utils.toUnitType
import com.example.collegeproject.viewmodels.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddProductViewModel(
    private val productDao: ProductDao,
    private val productId: Long
) : ViewModel() {
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        var product: Product? = null
        if (productId != 0L) {
            CoroutineScope(Dispatchers.IO).launch {
                product = productDao.getProductById(productId)
                withContext(Dispatchers.Main) {
                    if (product != null) {
                        productName = product!!.productName
                        sWageType = product!!.saleWageType.toMenuItem()
                        sWage = product!!.saleWage.toString()
                        sExtExpType = product!!.saleExtraExpenseType.toMenuItem()
                        sExtExp = product!!.saleExtraExpense.toString()
                        pWageType = product!!.purchaseWageType.toMenuItem()
                        pWage = product!!.purchaseWage.toString()
                        pCommissionType = product!!.purchaseCommissionType.toMenuItem()
                        pCommission = product!!.purchaseCommission.toString()
                        importFareType = product!!.purchaseImportFareType.toMenuItem()
                        importFare = product!!.purchaseImportFare.toString()
                        pExtExpType = product!!.purchaseExtraExpenseType.toMenuItem()
                        pExtExp = product!!.purchaseExtraExpense.toString()
                    }
                }
            }
        }
    }

    var productName by mutableStateOf("")
        private set

    fun onProductNameChange(productName: String) {
        this.productName = productName
    }

    var sWageType by mutableStateOf("")
        private set

    fun setSelectedSaleWageType(sWageType: String) {
        this.sWageType = sWageType
    }

    var sWageTypeExpand by mutableStateOf(false)
        private set

    fun onSaleWageDismiss() {
        sWageTypeExpand = false
    }

    fun onSaleWageExpandedChange() {
        sWageTypeExpand = !sWageTypeExpand
    }

    var sWage by mutableStateOf("")
        private set

    fun onSaleWageChange(sWage: String) {
        this.sWage = sWage
    }

    var sExtExpType by mutableStateOf("")
        private set

    fun setSelectedSaleExtraExpenseType(sExtExpType: String) {
        this.sExtExpType = sExtExpType
    }

    var sExtExpTypeExpand by mutableStateOf(false)
        private set

    fun onSaleExtraExpenseDismiss() {
        sExtExpTypeExpand = false
    }

    fun onSaleExtraExpenseExpandedChange() {
        sExtExpTypeExpand = !sExtExpTypeExpand
    }

    var sExtExp by mutableStateOf("")
        private set

    fun onSaleExtraExpenseChange(sExtExp: String) {
        this.sExtExp = sExtExp
    }

    var pWageType by mutableStateOf("")
        private set

    fun setSelectedPurchaseWageType(pWageType: String) {
        this.pWageType = pWageType
    }

    var pWageTypeExpand by mutableStateOf(false)
        private set

    fun onPurchaseWageDismiss() {
        pWageTypeExpand = false
    }

    fun onPurchaseWageExpandedChange() {
        pWageTypeExpand = !pWageTypeExpand
    }

    var pWage by mutableStateOf("")
        private set

    fun onPurchaseWageChange(pWage: String) {
        this.pWage = pWage
    }

    var pCommissionType by mutableStateOf("")
        private set

    fun setSelectedPurchaseCommissionType(pCommissionType: String) {
        this.pCommissionType = pCommissionType
    }

    var commisionTypeExpand by mutableStateOf(false)
        private set

    fun onCommissionDismiss() {
        commisionTypeExpand = false
    }

    fun onCommissionExpandedChange() {
        commisionTypeExpand = !commisionTypeExpand
    }

    var pCommission by mutableStateOf("")
        private set

    fun onPurchaseCommissionChange(pCommission: String) {
        this.pCommission = pCommission
    }

    var importFareType by mutableStateOf("")
        private set

    fun setSelectedImportFareType(importFareType: String) {
        this.importFareType = importFareType
    }

    var importFareTypeExpand by mutableStateOf(false)
        private set

    fun onImportFareDismiss() {
        importFareTypeExpand = false
    }

    fun onImportFareExpandedChange() {
        importFareTypeExpand = !importFareTypeExpand
    }

    var importFare by mutableStateOf("")
        private set

    fun onImportFareChange(importFare: String) {
        this.importFare = importFare
    }

    var pExtExpType by mutableStateOf("")
        private set

    fun setSelectedPurchaseExtraExpenseType(pExtExpType: String) {
        this.pExtExpType = pExtExpType
    }

    var pExtExpTypeExpand by mutableStateOf(false)
        private set

    fun onPurchaseExtraExpenseDismiss() {
        pExtExpTypeExpand = false
    }

    fun onPurchaseExtraExpenseExpandedChange() {
        pExtExpTypeExpand = !pExtExpTypeExpand
    }

    var pExtExp by mutableStateOf("")
        private set

    fun onPurchaseExtraExpenseChange(pExtExp: String) {
        this.pExtExp = pExtExp
    }

    fun onAddProductClick() {
        uiScope.launch {
            var product = Product(
                productName = productName,
                saleWage = sWage.toFloatOrNull() ?: 0f,
                saleWageType = sWageType.toUnitType(),
                saleExtraExpense = sExtExp.toFloatOrNull() ?: 0f,
                saleExtraExpenseType = sExtExpType.toUnitType(),
                purchaseWage = pWage.toFloatOrNull() ?: 0f,
                purchaseWageType = pWageType.toUnitType(),
                purchaseCommission = pCommission.toFloatOrNull() ?: 0f,
                purchaseCommissionType = pCommissionType.toUnitType(),
                purchaseImportFare = importFare.toFloatOrNull() ?: 0f,
                purchaseImportFareType = importFareType.toUnitType(),
                purchaseExtraExpense = pExtExp.toFloatOrNull() ?: 0f,
                purchaseExtraExpenseType = pExtExpType.toUnitType()
            )
            if (productId != 0L) product = product.copy(productId = productId)
            addProductToDatabase(product)
        }
    }

    private suspend fun addProductToDatabase(product: Product) {
        return withContext(Dispatchers.IO) {
            productDao.upsertProduct(product)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class AddProductViewModelFactory(
    private val dataSource: ProductDao,
    private val productId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddProductViewModel::class.java)) {
            return AddProductViewModel(dataSource, productId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}