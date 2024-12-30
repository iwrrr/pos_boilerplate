package com.example.pos_boilerplate.core.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pos_boilerplate.core.cache.converters.Converters
import com.example.pos_boilerplate.core.cache.dao.CartDao
import com.example.pos_boilerplate.core.cache.dao.ReceiptDao
import com.example.pos_boilerplate.core.cache.model.CartEntity
import com.example.pos_boilerplate.core.cache.model.ReceiptEntity
import com.example.pos_boilerplate.core.cache.model.ReceiptItemEntity

@Database(
    entities = [
        CartEntity::class,
        ReceiptEntity::class,
        ReceiptItemEntity::class,
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PosDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun receiptDao(): ReceiptDao

    companion object {
        const val DATABASE_NAME = "pos.db"
    }
}