package com.example.pos_boilerplate.core.domain.model

data class ReceiptItem(
    val id: Int,
    val receiptId: String,
    val productName: String,
    val quantity: Int,
    val subtotal: Long
)
