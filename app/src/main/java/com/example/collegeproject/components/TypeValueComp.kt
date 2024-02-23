package com.example.collegeproject.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.collegeproject.utils.UnitType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeValueComp(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onExpandedChange: () -> Unit,
    selectedOption: String,
    textFieldValue: String,
    labelText: String,
    onTypeChange: (String) -> Unit,
    onValueChange: (String) -> Unit,
) {
    Column {
        Text(text = labelText, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(start = 8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { onExpandedChange() },
                modifier = Modifier.weight(1f)
            ) {
                TextFieldComp(
                    textInput = selectedOption,
                    labelText = "Type",
                    onValueChange = onTypeChange,
                    modifier = Modifier.menuAnchor(),
                    expanded = expanded,
                    readOnly = true
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { onDismissRequest() }) {
                    UnitType.entries
                        .map { "${it.unit} (${it.acronym})" }
                        .forEach { unit ->
                            DropdownMenuItem(
                                text = { Text(text = unit) },
                                onClick = {
                                    onTypeChange(unit)
                                    onDismissRequest()
                                }
                            )
                        }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextFieldComp(
                textInput = textFieldValue,
                labelText = "Value",
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }
    }
}