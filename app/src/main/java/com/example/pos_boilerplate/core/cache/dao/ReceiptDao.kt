package com.example.pos_boilerplate.core.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pos_boilerplate.core.cache.model.ReceiptEntity
import com.example.pos_boilerplate.core.cache.model.ReceiptItemEntity
import com.example.pos_boilerplate.core.cache.model.ReceiptWithItemsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptDao {

    @Transaction
    @Query("SELECT * FROM receipts ORDER BY createdAt DESC")
    fun getAllReceipts() : Flow<List<ReceiptWithItemsEntity>>

    @Transaction
    @Query("SELECT * FROM receipts WHERE id = :id")
    fun getReceiptById(id: String) : Flow<ReceiptWithItemsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceipt(receiptEntity: ReceiptEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceiptItems(items: List<ReceiptItemEntity>)

    @Transaction
    @Query("SELECT * FROM receipts WHERE createdAt BETWEEN :startDate AND :endDate")
    fun getReceiptsByDateRange(startDate: Long, endDate: Long): Flow<List<ReceiptWithItemsEntity>>

    @Transaction
    @Query("SELECT SUM(totalAmount) FROM receipts WHERE createdAt BETWEEN :startDate AND :endDate")
    fun getTotalSalesByDateRange(startDate: Long, endDate: Long): Flow<Long?>

    @Transaction
    suspend fun checkout(receiptEntity: ReceiptEntity, items: List<ReceiptItemEntity>) {
        insertReceipt(receiptEntity)
        insertReceiptItems(items)
    }
}