package com.example.pos_boilerplate.core.domain.model

import com.example.pos_boilerplate.core.cache.model.ReceiptItem

data class ReceiptWithItems(
    val id: String,
    val totalAmount: Long,
    val paymentMethod: String,
    val customerName: String,
    val note: String,
    val createdAt: Long,
    val items: List<ReceiptItem>
)
