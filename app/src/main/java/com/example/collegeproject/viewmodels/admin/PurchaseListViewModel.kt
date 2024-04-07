package com.example.collegeproject.viewmodels.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.collegeproject.database.PurchaseSale
import com.example.collegeproject.database.PurchaseSaleDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class PurchaseListViewModel(
    private val purchaseSaleDao: PurchaseSaleDao
) : ViewModel() {
    private val _purchaseList = MutableLiveData<List<PurchaseSale>>()
    val purchaseList: LiveData<List<PurchaseSale>>
        get() = _purchaseList

    init {
        fetchData()
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            val purchaseListLiveData = purchaseSaleDao.getPurchaseList()
            withContext(Dispatchers.Main) {
                purchaseListLiveData.observeForever { list ->
                    _purchaseList.postValue(list)
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class PurchaseListViewModelFactory(
    private val purchaseSaleDao: PurchaseSaleDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PurchaseListViewModel::class.java)) {
            return PurchaseListViewModel(purchaseSaleDao) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}