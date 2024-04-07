package com.example.collegeproject.viewmodels.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.model.SellerProductListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class SellerProductListViewModel(
    private val purchaseSaleDao: PurchaseSaleDao,
) : ViewModel() {
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _sellerProductList = MutableLiveData<List<SellerProductListModel>>()
    val sellerProductList: LiveData<List<SellerProductListModel>>
        get() = _sellerProductList

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val listLiveData = purchaseSaleDao.getSellerProductList()

                listLiveData.observeForever { list ->
                    _sellerProductList.postValue(list)
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class SellerProductViewModelFactory(
    private val purchaseSaleDao: PurchaseSaleDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SellerProductListViewModel::class.java)) {
            return SellerProductListViewModel(purchaseSaleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}