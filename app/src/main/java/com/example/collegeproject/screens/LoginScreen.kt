package com.example.collegeproject.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.collegeproject.R
import com.example.collegeproject.components.HeaderTextComp
import com.example.collegeproject.components.NormalTextComp
import com.example.collegeproject.components.PasswordTextFieldComp
import com.example.collegeproject.components.PrimaryButtonComp
import com.example.collegeproject.components.StyledClickableTextComp
import com.example.collegeproject.components.TextFieldComp
import com.example.collegeproject.components.TopAppBarComp
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.utils.StartupScreen
import com.example.collegeproject.utils.UserPreferencesRepository
import com.example.collegeproject.utils.ValidationError
import com.example.collegeproject.utils.navigateWithPop
import com.example.collegeproject.viewmodels.LoginViewModel
import com.example.collegeproject.viewmodels.LoginViewModelFactory

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController?,
    userDao: UserDao?,
    userPreferencesRepository: UserPreferencesRepository?
) {
    val context = LocalContext.current
    val viewLifecycleOwner = LocalLifecycleOwner.current
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(userDao!!, userPreferencesRepository!!))
    val lifecycleOwner = LocalLifecycleOwner.current
    loginViewModel.toastMessage.observe(lifecycleOwner) { toastMessage ->
        if (toastMessage != "") {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
//            navController!!.navigate(Screen.Home.route) {
//                popUpTo(Screen.Login.route) {
//                    inclusive = true
//                }
//            }
//            loginViewModel.onLoginClickEventOver()
            loginViewModel.clearToastMessage()
        }
    }

    val userPreferencesLiveData = userPreferencesRepository.userPreferencesFlow.asLiveData()
    Scaffold(
        topBar = { TopAppBarComp(title = stringResource(id = R.string.login)) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(paddingValues = padding)
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                NormalTextComp(text = stringResource(id = R.string.hey_there))
                HeaderTextComp(text = stringResource(id = R.string.welcome_back))
                Spacer(modifier = Modifier.height(16.dp))
                TextFieldComp(
                    textInput = loginViewModel.email,
                    labelText = stringResource(id = R.string.email),
                    onValueChange = loginViewModel::onEmailChange,
                    leadingIcon = Icons.Outlined.Mail,
                    isError = loginViewModel.emailError != ValidationError.NONE,
                    errorType = loginViewModel.emailError
                )
                PasswordTextFieldComp(
                    passwordText = loginViewModel.password,
                    onPasswordChange = loginViewModel::onPasswordChange,
                    isError = loginViewModel.passwordError != ValidationError.NONE,
                    errorType = loginViewModel.passwordError
                )
                PrimaryButtonComp(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.login)
                ) {
                    loginViewModel.onLoginClickEvent()
                    userPreferencesLiveData.observe(viewLifecycleOwner) { userPreferences ->
                        if (userPreferences.userType == "Admin") {
                            navController?.navigateWithPop(StartupScreen.Admin.route, StartupScreen.Login.route)
                            Log.d("PREFS", userPreferences.toString())
                        } else if (userPreferences.userType != "") {
                            navController?.navigateWithPop(StartupScreen.Home.route.replace("{userType}", userPreferences.userType), StartupScreen.Login.route)
                            Log.d("PREFS", userPreferences.toString())
                        }
                    }
                }
                StyledClickableTextComp(infoText = "Not a user? ", actionText = "Sign Up") {
                    loginViewModel.onSignUpClickEvent()
                    loginViewModel.eventSignUpClick.observe(lifecycleOwner) { clicked ->
                        if (clicked) {
                            navController?.navigate(StartupScreen.SignUp.route)
                            loginViewModel.onSignUpClickEventOver()
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = null, userDao = null, userPreferencesRepository = null)
}