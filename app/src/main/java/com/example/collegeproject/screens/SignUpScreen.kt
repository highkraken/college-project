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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
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
import com.example.collegeproject.database.UserDatabase
import com.example.collegeproject.database.UserDatabaseDao
import com.example.collegeproject.utils.Screen
import com.example.collegeproject.utils.ValidationError
import com.example.collegeproject.viewmodels.SignUpViewModel
import com.example.collegeproject.viewmodels.SignUpViewModelFactory

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController?,
    userDatabaseDao: UserDatabaseDao?
) {
    val signUpViewModel: SignUpViewModel = viewModel(factory = SignUpViewModelFactory(userDatabaseDao!!))
    val viewLifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    signUpViewModel.toastMessage.observe(viewLifecycleOwner) { message ->
        if (message != "") {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            signUpViewModel.cleatToastMessage()
        }
    }

    Scaffold(
        topBar = { TopAppBarComp(title = stringResource(id = R.string.sign_up)) }
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
                    ),
                    isError = signUpViewModel.businessNameError != ValidationError.NONE,
                    errorType = signUpViewModel.businessNameError
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
                    ),
                    isError = signUpViewModel.emailError != ValidationError.NONE,
                    errorType = signUpViewModel.emailError
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
                    onPasswordChange = signUpViewModel::onPasswordChange,
                    isError = signUpViewModel.passwordError != ValidationError.NONE,
                    errorType = signUpViewModel.passwordError
                )
                RadioGroupComp(
                    options = listOf("Buyer", "Seller"),
                    label = "User Type:",
                    selectedOption = signUpViewModel.userType,
                    onOptionSelectChange = signUpViewModel::onUserTypeChange,
                    isError = signUpViewModel.userTypeError != ValidationError.NONE
                )
                PrimaryButtonComp(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.sign_up)
                ) {
                    signUpViewModel.onSignUpClickEvent()
                }
                StyledClickableTextComp(infoText = "Already a user? ", actionText = "Login") {
                    navController?.navigateUp()
                }
            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = null, userDatabaseDao = null)
}