package com.example.pos_boilerplate.features.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pos_boilerplate.core.common.state.Async
import com.example.pos_boilerplate.features.cart.components.CartItem
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartListScreen(
    viewModel: CartViewModel = koinViewModel(),
    navigateToReceipt: (receiptId: String) -> Unit,
    navigateBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(CartIntent.GetCartList)
    }

    LaunchedEffect(state.asyncCheckout) {
        when (val async = state.asyncCheckout) {
            is Async.Default -> {
                Timber.d("ASUUUUU ======> DEFAULT")
            }
            is Async.Loading -> {
                Timber.d("ASUUUUU ======> LOADING")
            }
            is Async.Success -> {
                navigateToReceipt(async.data)
            }
            is Async.Failure -> {
                Timber.d("ASUUUUU ======> FAILURE: ${async.throwable}")
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Keranjang")
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            when (val cartList = state.asyncCartList) {
                is Async.Success -> {
                    if (cartList.data.isNotEmpty()) {
                        BottomAppBar(
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    viewModel.sendIntent(CartIntent.Checkout(cartList.data))
                                }
                            ) {
                                Text(text = "Checkout")
                            }
                        }
                    }
                }

                else -> {}
            }

        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (val async = state.asyncCartList) {
                is Async.Default -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is Async.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is Async.Success -> {
                    items(async.data) { cart ->
                        CartItem(
                            cart = cart,
                            onReduceClick = {
                                viewModel.sendIntent(CartIntent.ReduceQuantity(cart))
                            },
                            onAddClick = {
                                viewModel.sendIntent(CartIntent.AddQuantity(cart))
                            },
                            onRemoveClick = {
                                viewModel.sendIntent(CartIntent.RemoveFromCart(cart))
                            }
                        )
                    }
                }

                is Async.Failure -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
