package com.example.pos_boilerplate.core.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipts")
data class ReceiptEntity(
    @PrimaryKey
    val id: String,
    val totalAmount: Long,
    val paymentMethod: String,
    val customerName: String? = null,
    val note: String? = null,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val createdAt: Long = System.currentTimeMillis()
)
