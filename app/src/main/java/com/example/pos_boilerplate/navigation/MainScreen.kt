package com.example.pos_boilerplate.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.pos_boilerplate.core.domain.model.Product
import com.example.pos_boilerplate.features.home.HomeScreen

@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    onProductClick: Product.() -> Unit,
    onCartClick: () -> Unit,
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Destination.Home,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            composable<Destination.Home> {
                HomeScreen(
                    onProductClick = onProductClick,
                    onCartClick = onCartClick
                )
            }
            composable<Destination.History> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "History")
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = remember {
        listOf(
            BottomScreens.Home,
            BottomScreens.History,
        )
    }

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            val selected = currentDestination?.hierarchy?.any {
                it.route == screen.route::class.qualifiedName
            } == true

            NavigationBarItem(
                icon = {
                    if (selected) {
                        Icon(screen.selectedIcon, contentDescription = null)
                    } else {
                        Icon(screen.unselectedIcon, contentDescription = null)
                    }
                },
                label = {
                    Text(text = screen.name)
                },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}