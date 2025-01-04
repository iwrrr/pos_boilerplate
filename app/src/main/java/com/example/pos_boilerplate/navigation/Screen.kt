package com.example.pos_boilerplate.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Main : Destination

    @Serializable
    data object Home : Destination

    @Serializable
    data object History : Destination

    @Serializable
    data class Receipt(val receiptId: String) : Destination

    @Serializable
    data object CartGraph : Destination {
        @Serializable
        data object CartList : Destination

        @Serializable
        data object Checkout : Destination
    }

    @Serializable
    data object ProductGraph : Destination {
        @Serializable
        data object ProductList : Destination

        @Serializable
        data object ProductDetail : Destination

        @Serializable
        data object MoreDetail : Destination
    }
}

sealed class BottomScreens<T>(
    val name: String,
    val route: T,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
) {
    data object Home : BottomScreens<Destination.Home>(
        name = "Home",
        route = Destination.Home,
        unselectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
    )

    data object History : BottomScreens<Destination.History>(
        name = "History",
        route = Destination.History,
        unselectedIcon = Icons.Outlined.History,
        selectedIcon = Icons.Filled.History,
    )
}