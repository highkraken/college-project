package com.example.collegeproject.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.collegeproject.R
import com.example.collegeproject.components.HeaderTextComp
import com.example.collegeproject.components.NormalTextComp
import com.example.collegeproject.components.PasswordTextFieldComp
import com.example.collegeproject.components.PrimaryButtonComp
import com.example.collegeproject.components.RadioGroupComp
import com.example.collegeproject.components.StyledClickableTextComp
import com.example.collegeproject.components.TextFieldComp
import com.example.collegeproject.components.TopAppBarComp
import com.example.collegeproject.utils.Screen
import com.example.collegeproject.viewmodels.SignUpViewModel

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController?
) {
    val signUpViewModel: SignUpViewModel = viewModel()
    Scaffold(
        topBar = { TopAppBarComp(title = stringResource(id = R.string.register)) }
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
                    .padding(16.dp)
            ) {
                NormalTextComp(text = stringResource(id = R.string.hey_there))
                HeaderTextComp(text = stringResource(id = R.string.create_an_account))
                Spacer(modifier = Modifier.height(16.dp))
                TextFieldComp(
                    textInput = signUpViewModel.businessName,
                    labelText = stringResource(id = R.string.business_name),
                    onValueChange = signUpViewModel::onBusinessNameChange,
                    leadingIcon = painterResource(
                        id = R.drawable.icon_business_name
                    )
                )
                TextFieldComp(
                    textInput = signUpViewModel.businessAddress,
                    labelText = stringResource(id = R.string.business_address),
                    onValueChange = signUpViewModel::onBusinessAddressChange,
                    minLines = 3,
                    maxLines = 5,
                    leadingIcon = painterResource(
                        id = R.drawable.icon_business_address
                    )
                )
                TextFieldComp(
                    textInput = signUpViewModel.ownerName,
                    labelText = stringResource(id = R.string.owner_name),
                    onValueChange = signUpViewModel::onOwnerNameChange,
                    leadingIcon = painterResource(
                        id = R.drawable.icon_owner_name
                    )
                )
                TextFieldComp(
                    textInput = signUpViewModel.email,
                    labelText = stringResource(id = R.string.email),
                    onValueChange = signUpViewModel::onEmailChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    leadingIcon = painterResource(
                        id = R.drawable.icon_email
                    )
                )
                TextFieldComp(
                    textInput = signUpViewModel.phoneNumber,
                    labelText = stringResource(id = R.string.phone),
                    onValueChange = signUpViewModel::onPhoneNumberChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    maxLength = 10,
                    leadingIcon = painterResource(
                        id = R.drawable.icon_phone
                    ),
                    isPhoneField = true
                )
                PasswordTextFieldComp(
                    passwordText = signUpViewModel.password,
                    onPasswordChange = signUpViewModel::onPasswordChange
                )
                RadioGroupComp(
                    options = listOf("Buyer", "Seller"),
                    label = "User Type:",
                    selectedOption = signUpViewModel.userType,
                    onOptionSelectChange = signUpViewModel::onUserTypeChange
                )
                PrimaryButtonComp(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.register)
                ) {
                    Log.d("SignUpScreen", signUpViewModel.toMap().toString())
                }
                StyledClickableTextComp(infoText = "Already a user? ", actionText = "Login") {
                    navController?.navigate(Screen.Login.route)
                }
            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = null)
}