package com.example.pos_boilerplate.features.cart

import com.example.pos_boilerplate.core.common.state.Async
import com.example.pos_boilerplate.core.domain.model.Cart

data class CartState(
    val asyncCartList: Async<List<Cart>> = Async.Default,
    val asyncUpdateQuantity: Async<Unit> = Async.Default,
    val asyncRemoveFromCart: Async<Unit> = Async.Default,
    val asyncCheckout: Async<String> = Async.Default,
)
