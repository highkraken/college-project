package com.example.collegeproject.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.database.User
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.model.PurchaseProductResult
import com.example.collegeproject.viewmodels.UserListViewModel
import com.example.collegeproject.viewmodels.UserListViewModelFactory

@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    userDao: UserDao,
    purchaseSaleDao: PurchaseSaleDao,
    userType: String
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val userListViewModel: UserListViewModel = viewModel(factory = UserListViewModelFactory(userDao, purchaseSaleDao, userType))
    var userList = listOf<User>()
    var products: List<List<String>> = listOf()

    userListViewModel.userList.observe(lifecycleOwner) { list ->
        if (list != null) {
            userList = list
        }
    }
    
    if (userType == "Seller") {
        products = userListViewModel.getProductsOfSeller()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        itemsIndexed(userList) { index, user ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Column {
                    Text(text = user.ownerName)
                    if (userType == "Seller" && products.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        products[index].forEach { product ->
                            AssistChip(onClick = { }, label = {
                                Text(text = product)
                            })
                        }
                    }
                }
            }
            Divider()
        }
    }
}