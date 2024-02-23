package com.example.collegeproject.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.collegeproject.utils.ValidationError

@OptIn(ExperimentalMaterial3Api::class)
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
    leadingIcon: ImageVector? = null,
    isPhoneField: Boolean = false,
    isError: Boolean = false,
    errorType: ValidationError = ValidationError.NONE,
    expanded: Boolean? = null,
    readOnly: Boolean = false,
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
        leadingIcon = if (leadingIcon != null) {
            {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = ""
                )
            }
        } else null,
        isError = isError,
        trailingIcon = if (expanded != null) {
            { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        } else null,
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
        },
        readOnly = readOnly
    )
}