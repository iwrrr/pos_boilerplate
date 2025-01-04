package com.example.pos_boilerplate.core.data.repository

import com.example.pos_boilerplate.core.cache.dao.CartDao
import com.example.pos_boilerplate.core.cache.model.CartEntity
import com.example.pos_boilerplate.core.common.repository.Repository
import com.example.pos_boilerplate.core.common.state.Async
import com.example.pos_boilerplate.core.data.mapper.toDomain
import com.example.pos_boilerplate.core.data.mapper.toEntity
import com.example.pos_boilerplate.core.domain.model.Cart
import com.example.pos_boilerplate.core.domain.model.Product
import com.example.pos_boilerplate.core.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CartRepositoryImpl(private val cartDao: CartDao) : Repository(), CartRepository {

    override fun getCartList(): Flow<Async<List<Cart>>> = flow {
        emit(Async.Loading)
        try {
            cartDao.getCartList()
                .map { entities -> entities.map(CartEntity::toDomain) }
                .collect { emit(Async.Success(it)) }
        } catch (e: Exception) {
            emit(Async.Failure(e))
        }
    }

    override fun getTotalCart(): Flow<Async<Int>> = flow {
        emit(Async.Loading)
        try {
            cartDao.getTotalCart().collect {
                if (it == null) {
                    emit(Async.Success(0))
                } else {
                    emit(Async.Success(it))
                }
            }
        } catch (e: Exception) {
            emit(Async.Failure(e))
        }
    }

    override fun addToCart(product: Product): Flow<Async<Unit>> = reduce {
        cartDao.getCartItem(product.id)?.let { existingItem ->
            // Update existing item
            val newQuantity = existingItem.quantity + 1
            updateCartEntity(existingItem, newQuantity)
        } ?: run {
            // Insert new item
            insertNewCartItem(product)
        }
    }

    override fun removeFromCart(cart: Cart): Flow<Async<Unit>> = reduce {
        cartDao.deleteCartItem(cart.toEntity())
    }

    override fun updateQuantity(cart: Cart): Flow<Async<Unit>> = reduce {
        val existingItem = cartDao.getCartItem(cart.productId)
            ?: throw Throwable("Cart not found")

        updateCartEntity(existingItem, cart.quantity)
    }

    // Helper functions
    private suspend fun updateCartEntity(entity: CartEntity, newQuantity: Int) {
        cartDao.updateCartItem(
            entity.copy(
                quantity = newQuantity,
                subtotal = entity.price * newQuantity,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    private suspend fun insertNewCartItem(product: Product) {
        val cart = CartEntity(
            productId = product.id,
            productName = product.name,
            productImage = product.image,
            quantity = 1,
            price = product.price,
            subtotal = product.price,
        )
        cartDao.insertCartItem(cart)
    }
}