package com.example.pos_boilerplate.core.cache.di

import android.content.Context
import androidx.room.Room
import com.example.pos_boilerplate.core.cache.PosDatabase
import com.example.pos_boilerplate.core.cache.dao.CartDao
import com.example.pos_boilerplate.core.cache.dao.ReceiptDao
import com.example.pos_boilerplate.core.cache.transaction.DatabaseTransactionProvider
import com.example.pos_boilerplate.core.cache.transaction.DatabaseTransactionProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val cacheModule = module {
    single { provideDatabase(androidContext()) }
    single { provideDatabaseTransactionProvider(get()) }
    single { provideCartDao(get()) }
    single { provideReceiptDao(get()) }
}

private fun provideDatabase(context: Context): PosDatabase {
    return Room.databaseBuilder(
        context,
        PosDatabase::class.java,
        PosDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}

private fun provideDatabaseTransactionProvider(
    database: PosDatabase
): DatabaseTransactionProvider {
    return DatabaseTransactionProviderImpl(database)
}

private fun provideCartDao(database: PosDatabase): CartDao {
    return database.cartDao()
}

private fun provideReceiptDao(database: PosDatabase): ReceiptDao {
    return database.receiptDao()
}