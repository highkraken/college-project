package com.example.collegeproject.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InvoiceItemDetailComp(
    modifier: Modifier = Modifier,
    productUnit: String = "Unit",
    productName: String = "Product",
    productQuantity: String = "Quantity",
    productPrice: String = "Price",
    productTotal: String = "Total",
    isTitle: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = productUnit,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = if (isTitle) TextStyle(fontWeight = FontWeight.Bold) else TextStyle.Default
        )
        Text(
            text = productName,
            modifier = Modifier.weight(3f),
            textAlign = TextAlign.Center,
            style = if (isTitle) TextStyle(fontWeight = FontWeight.Bold) else TextStyle.Default
        )
        Text(
            text = productQuantity,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.Center,
            style = if (isTitle) TextStyle(fontWeight = FontWeight.Bold) else TextStyle.Default
        )
        Text(
            text = productPrice,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.Center,
            style = if (isTitle) TextStyle(fontWeight = FontWeight.Bold) else TextStyle.Default
        )
        Text(
            text = productTotal,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.Center,
            style = if (isTitle) TextStyle(fontWeight = FontWeight.Bold) else TextStyle.Default
        )
    }
}

@Preview
@Composable
fun InvoiceItemDetailPreview() {
    InvoiceItemDetailComp()
}