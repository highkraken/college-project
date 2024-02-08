package com.example.collegeproject.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.collegeproject.R
import com.example.collegeproject.utils.ValidationError

@Composable
fun PasswordTextFieldComp(
    modifier: Modifier = Modifier,
    passwordText: String,
    onPasswordChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    isError: Boolean = false,
    errorType: ValidationError = ValidationError.NONE
) {
    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = passwordText,
        onValueChange = onPasswordChange,
        label = { Text(text = stringResource(id = R.string.password)) },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.icon_password),
                contentDescription = null
            )
        },
        trailingIcon = {
            val image =
                if (passwordVisibility)
                    Icons.Outlined.Visibility
                else
                    Icons.Outlined.VisibilityOff

            val description =
                if (passwordVisibility)
                    "Hide password"
                else
                    "Show password"

            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, description)
            }
        },
        isError = isError,
        supportingText = {
            val error = when (errorType) {
                ValidationError.EMPTY -> "Password" + errorType.errorMessage
                else -> errorType.errorMessage
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }
    )
}