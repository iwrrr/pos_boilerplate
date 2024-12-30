package com.example.pos_boilerplate.core.cache.model

data class ReceiptItem(
    val id: Int,
    val receiptId: String,
    val productName: String,
    val quantity: Int,
    val subtotal: Long
)
