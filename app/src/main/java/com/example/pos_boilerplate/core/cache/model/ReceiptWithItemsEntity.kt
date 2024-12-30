package com.example.pos_boilerplate.core.cache.model

import androidx.room.Embedded
import androidx.room.Relation

data class ReceiptWithItemsEntity(
    @Embedded
    val receipt: ReceiptEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "receiptId"
    )
    val items: List<ReceiptItemEntity>
)
