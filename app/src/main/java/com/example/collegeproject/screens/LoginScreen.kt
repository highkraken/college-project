package com.example.collegeproject.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.collegeproject.R
import com.example.collegeproject.components.HeaderTextComp
import com.example.collegeproject.components.NormalTextComp
import com.example.collegeproject.components.PasswordTextFieldComp
import com.example.collegeproject.components.PrimaryButtonComp
import com.example.collegeproject.components.StyledClickableTextComp
import com.example.collegeproject.components.TextFieldComp
import com.example.collegeproject.utils.Screen
import com.example.collegeproject.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController?
) {
    val loginViewModel: LoginViewModel = viewModel()
    val lifecycleOwner = LocalLifecycleOwner.current

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
            leadingIcon = painterResource(id = R.drawable.icon_email)
        )
        PasswordTextFieldComp(
            passwordText = loginViewModel.password,
            onPasswordChange = loginViewModel::onPasswordChange
        )
        PrimaryButtonComp(
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.login)) {
            Log.d("LoginScreen", "Login Clicked")
        }
        StyledClickableTextComp(infoText = "Not a user? ", actionText = "Sign Up") {
            loginViewModel.onLoginClickEvent()
            loginViewModel.eventSignUpClick.observe(lifecycleOwner) { clicked ->
                if (clicked) {
                    navController?.navigate(Screen.SignUp.route)
                    loginViewModel.onLoginClickEventOver()
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = null)
}