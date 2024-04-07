package com.example.collegeproject.viewmodels.admin

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
import java.time.LocalDate

class SaleListViewModel(
    private val purchaseSaleDao: PurchaseSaleDao
) : ViewModel() {
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _saleList = MutableLiveData<List<PurchaseSale>>()
    val saleList: LiveData<List<PurchaseSale>>
        get() = _saleList

    init {
        fetchData()
    }

    private fun fetchData() {
        uiScope.launch {
            var saleListLiveData: LiveData<List<PurchaseSale>>
            withContext(Dispatchers.IO) {
                saleListLiveData = purchaseSaleDao.getSaleList()
            }
            saleListLiveData.observeForever { list ->
                _saleList.postValue(list)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class SaleListViewModelFactory(
    private val purchaseSaleDao: PurchaseSaleDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SaleListViewModel::class.java)) {
            return SaleListViewModel(purchaseSaleDao) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}