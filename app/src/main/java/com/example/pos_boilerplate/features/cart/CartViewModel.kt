package com.example.pos_boilerplate.features.cart

import androidx.lifecycle.viewModelScope
import com.example.pos_boilerplate.core.common.viewmodel.Intent
import com.example.pos_boilerplate.core.common.viewmodel.ViewModelState
import com.example.pos_boilerplate.core.domain.model.Cart
import com.example.pos_boilerplate.core.domain.repository.CartRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
) : ViewModelState<CartState, CartIntent>(CartState()) {

    override fun sendIntent(intent: Intent) {
        when (intent) {
            is CartIntent.GetCartList -> {
                getCartList()
            }

            is CartIntent.AddQuantity -> {
                if (intent.cart.quantity >= 99) return
                addQuantity(intent.cart)
            }

            is CartIntent.ReduceQuantity -> {
                if (intent.cart.quantity > 1) {
                    reduceQuantity(intent.cart)
                } else {
                    removeFromCart(intent.cart)
                }
            }

            is CartIntent.RemoveFromCart -> {
                removeFromCart(intent.cart)
            }
        }
    }

    private fun getCartList() {
        viewModelScope.launch {
            cartRepository.getCartList()
                .stateIn(this)
                .collectLatest {
                    update {
                        copy(asyncCartList = it)
                    }
                }
        }
    }

    private fun addQuantity(cart: Cart) {
        val updatedCart = cart.copy(quantity = cart.quantity + 1)
        viewModelScope.launch {
            cartRepository.updateQuantity(updatedCart)
                .stateIn(this)
                .collectLatest {
                    update {
                        copy(asyncUpdateQuantity = it)
                    }
                }
        }
    }

    private fun reduceQuantity(cart: Cart) {
        val updatedCart = cart.copy(quantity = cart.quantity - 1)
        viewModelScope.launch {
            cartRepository.updateQuantity(updatedCart)
                .stateIn(this)
                .collectLatest {
                    update {
                        copy(asyncUpdateQuantity = it)
                    }
                }
        }
    }

    private fun removeFromCart(cart: Cart) {
        viewModelScope.launch {
            cartRepository.removeFromCart(cart)
                .stateIn(this)
                .collectLatest {
                    update {
                        copy(asyncRemoveFromCart = it)
                    }
                }
        }
    }
}