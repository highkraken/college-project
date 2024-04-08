package com.example.collegeproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.collegeproject.database.Product
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.database.User
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.model.PurchaseProductResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserListViewModel(
    private val userDao: UserDao,
    private val purchaseSaleDao: PurchaseSaleDao,
    private val userType: String
) : ViewModel() {
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = _userList

    private val _productsOfSellers = MutableLiveData<MutableList<List<PurchaseProductResult>>>(mutableListOf())
    val productsOfSellers: LiveData<MutableList<List<PurchaseProductResult>>>
        get() = _productsOfSellers

    init {
        fetchData()
    }

    fun getProductsOfSeller(): List<List<String>> {
        val products = mutableListOf<List<String>>()
        var users = listOf<User>()
        userList.observeForever {
            users = it
        }
        uiScope.launch {
            users.forEach { user ->
                val prods = fetchProductsOfSeller(user.userId)
                products.add(prods ?: listOf())
            }
        }
        return products.toList()
    }

    private suspend fun fetchProductsOfSeller(sellerId: Long): List<String>? {
        return withContext(Dispatchers.IO) {
            purchaseSaleDao.getProductsOfSeller(sellerId).value?.map { it.productName }
        }
    }

    private fun fetchData() {
        uiScope.launch {
            var userListLiveData: LiveData<List<User>>
            withContext(Dispatchers.IO) {
                userListLiveData = userDao.getUsersByType(userType)
            }
            userListLiveData.observeForever { list ->
                _userList.postValue(list)
                uiScope.launch {
                    list.forEach { user ->
                        var products: LiveData<List<PurchaseProductResult>>
                        withContext(Dispatchers.IO) {
                            products = purchaseSaleDao.getProductsOfSeller(user.userId)
                        }
                        products.observeForever {
                            val newProducts = _productsOfSellers.value?.apply { add(it) }
                            Log.d("VMMMM", newProducts.toString())
                            _productsOfSellers.postValue(newProducts!!)
                        }
                    }
                }
            }
//            withContext(Dispatchers.IO) {
//                _userList.value?.forEach { user ->
//                    val products = purchaseSaleDao.getProductsOfSeller(user.userId)
//                    withContext(Dispatchers.Main) {
//
//                    }
//                }
//            }
//            _userList.value?.forEach { user ->
//                var products: LiveData<List<PurchaseProductResult>>
//                withContext(Dispatchers.IO) {
//                    products = purchaseSaleDao.getProductsOfSeller(user.userId)
//                }
//                products.observeForever {
//                    val newProducts = _productsOfSellers.value?.apply { add(it) }
//                    Log.d("VMMMM", newProducts.toString())
//                    _productsOfSellers.postValue(newProducts!!)
//                }
//            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class UserListViewModelFactory(
    private val userDao: UserDao,
    private val purchaseSaleDao: PurchaseSaleDao,
    private val userType: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            return UserListViewModel(userDao, purchaseSaleDao, userType) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}