package com.example.pos_boilerplate.core.domain.model

data class Receipt(
    val id: String,
    val totalAmount: Long,
    val paymentMethod: String,
    val customerName: String,
    val note: String,
    val createdAt: Long,
    val items: List<ReceiptItem>
)
