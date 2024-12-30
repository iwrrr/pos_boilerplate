package com.example.pos_boilerplate.core.data.mapper

import com.example.pos_boilerplate.core.cache.model.ReceiptItem
import com.example.pos_boilerplate.core.cache.model.ReceiptItemEntity
import com.example.pos_boilerplate.core.cache.model.ReceiptWithItemsEntity
import com.example.pos_boilerplate.core.domain.model.ReceiptWithItems

fun ReceiptWithItemsEntity.toDomain(): ReceiptWithItems {
    return ReceiptWithItems(
        id = receipt.id,
        totalAmount = receipt.totalAmount,
        paymentMethod = receipt.paymentMethod,
        customerName = receipt.customerName.orEmpty(),
        note = receipt.note.orEmpty(),
        createdAt = receipt.createdAt,
        items = items.map(ReceiptItemEntity::toDomain)
    )
}

fun ReceiptItemEntity.toDomain(): ReceiptItem {
    return ReceiptItem(
        id = id,
        receiptId = receiptId,
        productName = productName,
        quantity = quantity,
        subtotal = subtotal
    )
}