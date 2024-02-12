package com.example.collegeproject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.collegeproject.R
import com.example.collegeproject.components.HeaderTextComp
import com.example.collegeproject.components.HomeTileComp
import com.example.collegeproject.components.TopAppBarComp
import com.example.collegeproject.utils.Screen
import com.example.collegeproject.utils.UserPreferences
import com.example.collegeproject.utils.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController?,
    userPreferencesRepository: UserPreferencesRepository?,
) {
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
                title = "Sellers",
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
                    navController?.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = true
                        }
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        userPreferencesRepository?.updateUserPreferences(
                            UserPreferences()
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = null, userPreferencesRepository = null)
}