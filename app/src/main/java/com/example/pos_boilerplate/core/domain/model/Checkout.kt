package com.example.pos_boilerplate.core.domain.model

data class Checkout(
    val items: List<Cart>,
    val totalAmount: Long,
    val paymentMethod: PaymentMethod,
    val customerName: String? = null,
    val note: String? = null,
)
