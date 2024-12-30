package com.example.pos_boilerplate.features.cart.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuantityButton(
    quantity: String,
    onReduceClick: () -> Unit,
    onAddClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedIconButton(
            onClick = {
                onReduceClick()
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Remove,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = quantity)
        Spacer(modifier = Modifier.width(8.dp))
        FilledIconButton(
            onClick = {
                onAddClick()
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null
            )
        }
    }
}