package com.example.collegeproject.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RadioGroupComp(
    modifier: Modifier = Modifier,
    options: List<String>,
    label: String,
    selectedOption: String,
    onOptionSelectChange: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .selectable(
                        selected = option == selectedOption,
                        onClick = {
                            onOptionSelectChange(option)
                        }
                    )
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = {
                        onOptionSelectChange(option)
                    }
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}