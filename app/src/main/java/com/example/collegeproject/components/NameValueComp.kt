package com.example.collegeproject.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun NameValueComp(
    modifier: Modifier = Modifier,
    name: String,
    value: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
//            .padding(vertical = 5.dp)
    ) {
        Text(text = name, modifier = Modifier.weight(1f), style = TextStyle.Default)
        Text(text = value, modifier = Modifier.weight(1f), style = TextStyle.Default, textAlign = TextAlign.End)
    }
}