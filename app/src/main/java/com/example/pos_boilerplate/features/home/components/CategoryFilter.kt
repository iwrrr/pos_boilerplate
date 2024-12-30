package com.example.pos_boilerplate.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset

@Composable
fun CategoryFilter(modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    val offsetPx = with(density) {
        16.dp.roundToPx()
    }

    var selectedIndex by remember { mutableIntStateOf(0) }

    LazyRow(
        modifier = Modifier.layout { measurable, constraints ->
            val looseConstraints = constraints.offset(offsetPx * 2, 0)
            val placeable = measurable.measure(looseConstraints)
            layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(10) { index ->
            FilterChip(
                selected = index == selectedIndex,
                label = { Text(text = "Item $index") },
                onClick = {
                    selectedIndex = index;
                }
            )
        }
    }
}
