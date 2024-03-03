package com.example.collegeproject.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.PopupProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownFieldComp(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onExpandedChange: () -> Unit,
    selectedOption: String,
    labelText: String,
    onTypeChange: (String) -> Unit,
    dropdownList: List<String>,
    readOnly: Boolean
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange() }
    ) {
        TextFieldComp(
            textInput = selectedOption,
            labelText = labelText,
            onValueChange = { onTypeChange(it) },
            modifier = modifier.menuAnchor(),
            expanded = expanded,
            readOnly = readOnly
        )
        DropdownMenu(
            modifier = Modifier
                .exposedDropdownSize(true),
            properties = PopupProperties(focusable = false),
            expanded = expanded,
            onDismissRequest = { onDismissRequest() }) {
            dropdownList.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        onTypeChange(item)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}