package com.example.pos_boilerplate.core.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
data class CartEntity(
    @PrimaryKey
    val productId: Int,
    val productName: String,
    val productImage: String,
    val quantity: Int,
    val price: Long,
    val subtotal: Long,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long? = null,
)
