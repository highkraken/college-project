package com.example.collegeproject.viewmodels.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.collegeproject.database.PurchaseSale
import com.example.collegeproject.database.PurchaseSaleDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class PurchaseSaleListViewModel(
    private val purchaseSaleDao: PurchaseSaleDao,
    private val sellerId: Long,
    private val productId: Long,
    private val date: LocalDate,
) : ViewModel() {
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _purchaseSaleList = MutableLiveData<List<PurchaseSale>>()
    val purchaseSaleList: LiveData<List<PurchaseSale>>
        get() = _purchaseSaleList

    var sellerName: String = ""
    var productName: String = ""

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val listLiveData = purchaseSaleDao.getPurchaseSaleList(
                    sellerId = sellerId,
                    productId = productId,
                    date = date
                )

                listLiveData.observeForever { list ->
                    _purchaseSaleList.postValue(list)
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class PurchaseSaleListViewModelFactory(
    private val purchaseSaleDao: PurchaseSaleDao,
    private val sellerId: Long,
    private val productId: Long,
    private val date: LocalDate = LocalDate.now(),
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PurchaseSaleListViewModel::class.java)) {
            return PurchaseSaleListViewModel(purchaseSaleDao, sellerId, productId, date) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}