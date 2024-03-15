package com.example.collegeproject.viewmodels.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.model.PurchaseProductResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PurchaseDetailViewModel(
    private val purchaseSaleDao: PurchaseSaleDao,
    private val userDao: UserDao,
    private val sellerId: Long
) : ViewModel() {
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var sellerName: String = ""
        private set

    var date: LocalDate = LocalDate.parse("2024-03-04")
        private set

    var commission = 0f
        private set

    var labour = 0f
        private set

    var importFare = 0f
        private set

    var extraExpense = 0f
        private set

    var total = 0f
        private set

    var totalAfterExpenses = 0f
        private set

    var grandTotal = 0f
        private set

    private val _products = MutableLiveData<List<PurchaseProductResult>>()
    val products: LiveData<List<PurchaseProductResult>>
        get() = _products

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            sellerName = userDao.getUserById(sellerId).ownerName
            withContext(Dispatchers.Main) {
                purchaseSaleDao.getProductsOfSeller(sellerId, date).observeForever { productResult ->
                    _products.postValue(productResult)
                    productResult.forEach { product ->
                        commission += product.commissionValue
                        labour += product.labourValue
                        extraExpense += product.extraExpenseValue
                        importFare += product.importFareValue
                        total += product.productTotal
                        totalAfterExpenses += commission + labour + extraExpense + importFare
                        grandTotal = total - totalAfterExpenses
                    }
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class PurchaseDetailViewModelFactory(
    private val purchaseSaleDao: PurchaseSaleDao,
    private val userDao: UserDao,
    private val sellerId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PurchaseDetailViewModel::class.java)) {
            return PurchaseDetailViewModel(
                purchaseSaleDao,
                userDao,
                sellerId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}