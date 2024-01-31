package com.example.collegeproject.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldComp(
    modifier: Modifier = Modifier,
    labelText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    maxLines: Int = 1,
    minLines: Int = 1,
    maxLength: Int = Int.MAX_VALUE,
    leadingIcon: Painter
) {
    var textInput by rememberSaveable {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        value = textInput,
        onValueChange = {
            if (it.length <= maxLength) textInput = it
        },
        label = { Text(text = labelText) },
        keyboardOptions = keyboardOptions,
        singleLine = minLines == 1,
        maxLines = maxLines,
        minLines = minLines,
        leadingIcon = { Icon(painter = leadingIcon, contentDescription = null) }
    )
}