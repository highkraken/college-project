package com.example.collegeproject.screens.admin.datainput

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.collegeproject.utils.AdminNavigation

@Composable
fun DataInputScreen(
    navController: NavHostController? = null
) {
    val dataInputItems = listOf("Product", "Purchase-Sale", "Purchase", "Sale", "Credit")
    val dataInputRoutes = listOf(AdminNavigation.Product.route, AdminNavigation.PurchaseSale.route, AdminNavigation.Purchase.route, AdminNavigation.Sale.route, AdminNavigation.Credit.route)
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        itemsIndexed(dataInputItems) { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { navController?.navigate(dataInputRoutes[index]) }
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "",
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                    )
                    Text(text = item)
                }
            }
        }
    }
}


@Preview
@Composable
fun DataInputPreview() {
    DataInputScreen()
}

data class DataInputItem(
    val title: String,
    val icon: ImageVector,
)