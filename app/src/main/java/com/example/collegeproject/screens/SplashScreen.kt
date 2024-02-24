package com.example.collegeproject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.collegeproject.R
import com.example.collegeproject.utils.StartupScreen
import com.example.collegeproject.utils.UserPreferencesRepository
import com.example.collegeproject.utils.navigateWithPop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    userPreferencesRepository: UserPreferencesRepository,
    navController: NavController,
) {
    val userType = MutableLiveData("")
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = true) {
        userType.value = withContext(Dispatchers.IO) {
            userPreferencesRepository.loggedInAs()
        }
        delay(700)
        userType.observe(lifecycleOwner) { type ->
            if (type?.isNotEmpty() == true) {
                if (type == "Admin") {
                    navController.navigateWithPop(StartupScreen.Admin.route, StartupScreen.Splash.route)
                } else {
                    navController.navigateWithPop(
                        StartupScreen.Home.route.replace("{userType}", type),
                        StartupScreen.Splash.route
                    )
                }
            } else {
                navController.navigateWithPop(StartupScreen.Login.route, StartupScreen.Splash.route)
            }
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
    }
}