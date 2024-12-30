package com.example.pos_boilerplate.core.domain.model

data class Cart(
    val productId: Int,
    val productName: String,
    val productImage: String,
    val quantity: Int,
    val price: Long,
    val subtotal: Long,
)
