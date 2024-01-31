package com.example.collegeproject.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.collegeproject.R
import com.example.collegeproject.components.HeaderTextComp
import com.example.collegeproject.components.NormalTextComp
import com.example.collegeproject.components.PasswordTextFieldComp
import com.example.collegeproject.components.PrimaryButtonComp
import com.example.collegeproject.components.TextFieldComp

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        NormalTextComp(text = stringResource(id = R.string.hey_there))
        HeaderTextComp(text = stringResource(id = R.string.create_an_account))
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComp(
            labelText = stringResource(id = R.string.business_name),
            leadingIcon = painterResource(
                id = R.drawable.icon_business_name
            )
        )
        TextFieldComp(
            labelText = stringResource(id = R.string.business_address),
            minLines = 3,
            maxLines = 5,
            leadingIcon = painterResource(
                id = R.drawable.icon_business_address
            )
        )
        TextFieldComp(
            labelText = stringResource(id = R.string.owner_name),
            leadingIcon = painterResource(
                id = R.drawable.icon_owner_name
            )
        )
        TextFieldComp(
            labelText = stringResource(id = R.string.email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = painterResource(
                id = R.drawable.icon_email
            )
        )
        TextFieldComp(
            labelText = stringResource(id = R.string.phone),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            maxLength = 10,
            leadingIcon = painterResource(
                id = R.drawable.icon_phone
            )
        )
        PasswordTextFieldComp()
        PrimaryButtonComp(
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.register)
        ) {
            Log.d("SignUpScreen", "Register Clicked")
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}