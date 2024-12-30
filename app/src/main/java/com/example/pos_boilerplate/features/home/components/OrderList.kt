package com.example.pos_boilerplate.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset

@Composable
fun OrderList(modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    val offsetPx = with(density) {
        16.dp.roundToPx()
    }

    Column {
        Text(text = "Order List")
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.layout { measurable, constraints ->
                val looseConstraints = constraints.offset(offsetPx * 2, 0)
                val placeable = measurable.measure(looseConstraints)
                layout(placeable.width, placeable.height) {
                    placeable.placeRelative(0, 0)
                }
            },
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(count = 5) { index ->
                Card(
                    modifier = Modifier
                        .height(120.dp)
                        .aspectRatio(2f)
                ) {
                    Text(text = "Item $index")
                }
            }
        }
    }
}
