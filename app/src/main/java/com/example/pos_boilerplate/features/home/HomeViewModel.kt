package com.example.pos_boilerplate.features.home

import androidx.lifecycle.viewModelScope
import com.example.pos_boilerplate.core.common.viewmodel.Intent
import com.example.pos_boilerplate.core.common.viewmodel.ViewModelState
import com.example.pos_boilerplate.core.domain.model.Product
import com.example.pos_boilerplate.core.domain.repository.CartRepository
import com.example.pos_boilerplate.core.domain.repository.ProductRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModelState<HomeState, HomeIntent>(HomeState()) {

    override fun sendIntent(intent: Intent) {
        when (intent) {
            is HomeIntent.GetProductList -> {
                getProductList()
            }

            is HomeIntent.GetTotalCart -> {
                getTotalCart()
            }

            is HomeIntent.AddToCart -> {
                addToCart(intent.item)
            }
        }
    }

    private fun getProductList() {
        viewModelScope.launch {
            productRepository.getProductList()
                .stateIn(this)
                .collectLatest {
                    update {
                        copy(asyncProductList = it)
                    }
                }
        }
    }

    private fun getTotalCart() {
        viewModelScope.launch {
            cartRepository.getTotalCart()
                .stateIn(this)
                .collectLatest {
                    update {
                        copy(asyncCartList = it)
                    }
                }
        }
    }

    private fun addToCart(item: Product) {
        viewModelScope.launch {
            cartRepository.addToCart(item)
                .stateIn(this)
                .collectLatest {
                    update {
                        copy(asyncAddToCart = it)
                    }
                }
        }
    }
}