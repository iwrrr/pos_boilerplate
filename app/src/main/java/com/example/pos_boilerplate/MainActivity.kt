package com.example.pos_boilerplate

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.pos_boilerplate.features.cart.CartListScreen
import com.example.pos_boilerplate.navigation.Destination
import com.example.pos_boilerplate.navigation.MainScreen
import com.example.pos_boilerplate.ui.theme.PosTheme
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PosTheme {
                MainApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.Main,
    ) {
        composable<Destination.Main> {
            MainScreen(
                onProductClick = {
                    navController.navigate(Destination.Test)
                },
                onCartClick = {
                    navController.navigate(Destination.CartGraph.CartList)
                }
            )
        }
        composable<Destination.Test> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        navController.navigateUp()
                    }
                ) {
                    Text(text = "Back to prev screen")
                }
            }
        }
        navigation<Destination.CartGraph>(
            startDestination = Destination.CartGraph.CartList,
        ) {
            composable<Destination.CartGraph.CartList> {
                CartListScreen(
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
            composable<Destination.CartGraph.Checkout> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "More Detail")
                }
            }
        }
        navigation<Destination.ProductGraph>(
            startDestination = Destination.ProductGraph.ProductList,
        ) {
            composable<Destination.ProductGraph.ProductList> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Product List")
                }
            }
            composable<Destination.ProductGraph.ProductDetail> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Product Detail")
                }
            }
            composable<Destination.ProductGraph.MoreDetail> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "More Detail")
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return viewModel(parentEntry)
}