package com.example.pos_boilerplate.features.cart

import com.example.pos_boilerplate.core.common.viewmodel.Intent
import com.example.pos_boilerplate.core.domain.model.Cart

sealed class CartIntent : Intent {
    data object GetCartList : CartIntent()
    data class AddQuantity(val cart: Cart) : CartIntent()
    data class ReduceQuantity(val cart: Cart) : CartIntent()
    data class RemoveFromCart(val cart: Cart) : CartIntent()
    data class Checkout(val cartList: List<Cart>) : CartIntent()
}
