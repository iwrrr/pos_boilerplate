package com.example.pos_boilerplate.core.data.mapper

import com.example.pos_boilerplate.core.cache.model.CartEntity
import com.example.pos_boilerplate.core.domain.model.Cart

fun Cart.toEntity(): CartEntity {
    return CartEntity(
        productId = productId,
        productName = productName,
        productImage = productImage,
        quantity = quantity,
        price = price,
        subtotal = subtotal,
    )
}

fun CartEntity.toDomain(): Cart {
    return Cart(
        productId = productId,
        productName = productName,
        productImage = productImage,
        quantity = quantity,
        price = price,
        subtotal = subtotal,
    )
}