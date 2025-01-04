package com.example.pos_boilerplate.core.domain.repository

import com.example.pos_boilerplate.core.common.state.Async
import com.example.pos_boilerplate.core.domain.model.Cart
import com.example.pos_boilerplate.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartList(): Flow<Async<List<Cart>>>
    fun getTotalCart(): Flow<Async<Int>>
    fun addToCart(product: Product): Flow<Async<Unit>>
    fun removeFromCart(cart: Cart): Flow<Async<Unit>>
    fun updateQuantity(cart: Cart): Flow<Async<Unit>>
}