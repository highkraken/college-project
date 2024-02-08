package com.example.collegeproject.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.collegeproject.utils.ValidationError

@Composable
fun TextFieldComp(
    modifier: Modifier = Modifier,
    textInput: String,
    labelText: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    maxLines: Int = 1,
    minLines: Int = 1,
    maxLength: Int = Int.MAX_VALUE,
    leadingIcon: Painter,
    isPhoneField: Boolean = false,
    isError: Boolean = false,
    errorType: ValidationError = ValidationError.NONE
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = textInput,
        onValueChange = {
            if (it.length < maxLength) onValueChange(it)
        },
        label = { Text(text = labelText) },
        keyboardOptions = keyboardOptions.copy(imeAction = ImeAction.Next),
        singleLine = minLines == 1,
        maxLines = maxLines,
        minLines = minLines,
        prefix = { Text(text = if (isPhoneField) "+91 " else "") },
        leadingIcon = { Icon(painter = leadingIcon, contentDescription = null) },
        isError = isError,
        supportingText = {
            if (isError) {
                val error = when (errorType) {
                    ValidationError.EMPTY -> labelText + errorType.errorMessage
                    else -> errorType.errorMessage
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    )
}