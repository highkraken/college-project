package com.example.collegeproject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.collegeproject.R
import com.example.collegeproject.components.HomeTileComp
import com.example.collegeproject.components.TopAppBarComp
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.utils.StartupScreen
import com.example.collegeproject.utils.UserPreferencesRepository
import com.example.collegeproject.utils.navigateWithPop
import com.example.collegeproject.viewmodels.HomeViewModel
import com.example.collegeproject.viewmodels.HomeViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController?,
    userDao: UserDao?,
    userPreferencesRepository: UserPreferencesRepository?,
    userType: String,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(userDao!!, userPreferencesRepository!!))
    val getType = if (userType == "Sellers") "Buyer" else "Seller"
    Scaffold(
        topBar = { TopAppBarComp(title = "Home") },
        ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            HomeTileComp(
                title = userType,
                leadingIcon = painterResource(id = R.drawable.icon_owner_name)
            )
            HomeTileComp(
                title = "Invoice",
                leadingIcon = painterResource(id = R.drawable.icon_business_name)
            )
            HomeTileComp(
                title = "Logout",
                leadingIcon = painterResource(id = R.drawable.icon_logout),
                onClick = {
                    navController?.navigateWithPop(StartupScreen.Login.route, StartupScreen.Home.route)
                    homeViewModel.clearUserPreferences()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = null, userPreferencesRepository = null, userType = "Seller", userDao = null)
}