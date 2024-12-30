package com.example.pos_boilerplate.core.cache.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "receipt_items",
    foreignKeys = [
        ForeignKey(
            entity = ReceiptEntity::class,
            parentColumns = ["id"],
            childColumns = ["receiptId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index("receiptId")
    ]
)
data class ReceiptItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val receiptId: String,
    val productName: String,
    val quantity: Int,
    val subtotal: Long
)
