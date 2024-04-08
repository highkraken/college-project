package com.example.collegeproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.collegeproject.database.PurchaseSale
import com.example.collegeproject.database.PurchaseSaleDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InvoiceListViewModel(
    private val purchaseSaleDao: PurchaseSaleDao,
    private val userType: String,
    private val userId: Long
) : ViewModel() {
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _invoiceList = MutableLiveData<List<PurchaseSale>>()
    val invoiceList: LiveData<List<PurchaseSale>>
        get() = _invoiceList

    init {
        fetchData()
    }

    private fun fetchData() {
        uiScope.launch {
            val invoiceListLiveData = getInvoiceList()
            invoiceListLiveData.observeForever { list ->
                if (list != null) {
                    _invoiceList.postValue(list)
                }
            }
        }
    }

    private suspend fun getInvoiceList(): LiveData<List<PurchaseSale>> {
        return withContext(Dispatchers.IO) {
            if (userType == "Seller") {
                purchaseSaleDao.getPurchaseInvoiceList(userId)
            } else {
                purchaseSaleDao.getSaleInvoiceList(userId)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class InvoiceListViewModelFactory(
    private val purchaseSaleDao: PurchaseSaleDao,
    private val userType: String,
    private val userId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InvoiceListViewModel::class.java)) {
            return InvoiceListViewModel(purchaseSaleDao, userType, userId) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}