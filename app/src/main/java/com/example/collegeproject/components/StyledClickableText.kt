package com.example.collegeproject.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun StyledClickableTextComp(
    modifier: Modifier = Modifier,
    infoText: String,
    actionText: String,
    onActionClick: () -> Unit,
) {
    val text = buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.onSurface
            )
        ) {
            pushStringAnnotation(tag = infoText, annotation = infoText)
            append(infoText)
        }
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        ) {
            pushStringAnnotation(tag = actionText, annotation = actionText)
            append(actionText)
        }
    }

    ClickableText(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        style = TextStyle(textAlign = TextAlign.Center),
        text = text,
        onClick = { offset ->
            text.getStringAnnotations(start = offset, end = offset)
                .firstOrNull()?.also {
                    onActionClick()
                }
        }
    )
}