package com.example.pos_boilerplate.features.home

import com.example.pos_boilerplate.core.common.state.Async
import com.example.pos_boilerplate.core.domain.model.Cart
import com.example.pos_boilerplate.core.domain.model.Product

data class HomeState(
    val asyncProductList: Async<List<Product>> = Async.Default,
    val asyncCartList: Async<List<Cart>> = Async.Default,
    val asyncAddToCart: Async<Unit> = Async.Default,
)
