package com.example.collegeproject.viewmodels.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.collegeproject.database.Product
import com.example.collegeproject.database.ProductDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListViewModel(private val productDao: ProductDao) : ViewModel() {
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>>
        get() = _productList

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            val productsLiveData = productDao.getAllProducts()
            withContext(Dispatchers.Main) {
                productsLiveData.observeForever { products ->
                    _productList.postValue(products)
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ProductListViewModelFactory(
    private val productDao: ProductDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            return ProductListViewModel(productDao) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}