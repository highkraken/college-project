package com.example.collegeproject.viewmodels.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.model.SaleProductResult
import com.example.collegeproject.utils.sumOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class SaleDetailViewModel(
    private val purchaseSaleDao: PurchaseSaleDao,
    private val userDao: UserDao,
    private val buyerId: Long
) : ViewModel() {
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var buyerName: String = ""
        private set

    var date: LocalDate = LocalDate.now()
        private set

    var labour = 0f
        private set

    var extraExpense = 0f
        private set

    var total = 0f
        private set

    var totalAfterExpenses = 0f
        private set

    var grandTotal = 0f
        private set

    private val _products = MutableLiveData<List<SaleProductResult>>()
    val products: LiveData<List<SaleProductResult>>
        get() = _products

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            buyerName = userDao.getUserById(buyerId).ownerName
            withContext(Dispatchers.Main) {
                purchaseSaleDao.getProductsOfBuyer(buyerId, date).observeForever { productResult ->
                    _products.postValue(productResult)
                    calculateExpenses(productResult)
                }
            }
        }
    }

    private fun calculateExpenses(products: List<SaleProductResult>) {
        labour = products.sumOf { it.labourValue }
        extraExpense = products.sumOf { it.extraExpenseValue }
        total = products.sumOf { it.productTotal }
        totalAfterExpenses = labour + extraExpense
        grandTotal = total + totalAfterExpenses
    }
}

//private fun <T> Iterable<T>.sumOf(function: () -> Float): Float {
//    return this.sumOf {  }
//}

@Suppress("UNCHECKED_CAST")
class SaleDetailViewModelFactory(
    private val purchaseSaleDao: PurchaseSaleDao,
    private val userDao: UserDao,
    private val buyerId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SaleDetailViewModel::class.java)) {
            return SaleDetailViewModel(
                purchaseSaleDao,
                userDao,
                buyerId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}