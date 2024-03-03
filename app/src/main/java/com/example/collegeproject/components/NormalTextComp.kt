package com.example.collegeproject.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.collegeproject.ui.theme.Typography

@Composable
fun NormalTextComp(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth(),
        style = Typography.headlineSmall,
        textAlign = TextAlign.Center
    )
}