package com.example.pos_boilerplate.core.cache.transaction

import androidx.room.withTransaction
import com.example.pos_boilerplate.core.cache.PosDatabase

class DatabaseTransactionProviderImpl(
    private val database: PosDatabase
) : DatabaseTransactionProvider {

    private val transactionThread = ThreadLocal<Boolean>()

    override suspend fun <T> runAsTransaction(block: suspend () -> T): T {
        return try {
            if (isInTransaction()) {
                block()
            } else {
                database.withTransaction {
                    setTransactionActive(true)
                    try {
                        block().also {
                            setTransactionActive(false)
                        }
                    } catch (e: Exception) {
                        setTransactionActive(false)
                        throw e
                    }
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun ensureTransaction(block: suspend () -> Unit) {
        if (!isInTransaction()) {
            runAsTransaction(block)
        } else {
            block()
        }
    }

    override fun isInTransaction(): Boolean {
        return transactionThread.get() == true
    }

    private fun setTransactionActive(active: Boolean) {
        transactionThread.set(active)
    }
}