package com.example.pos_boilerplate.core.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pos_boilerplate.core.cache.model.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM carts ORDER BY createdAt ASC")
    fun getCartList(): Flow<List<CartEntity>>

    @Query("SELECT * FROM carts WHERE productId = :productId")
    suspend fun getCartItem(productId: Int): CartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartEntity: CartEntity)

    @Update
    suspend fun updateCartItem(cartEntity: CartEntity)

    @Delete
    suspend fun deleteCartItem(cartEntity: CartEntity)

    @Query("DELETE FROM carts")
    suspend fun clearCart()
}