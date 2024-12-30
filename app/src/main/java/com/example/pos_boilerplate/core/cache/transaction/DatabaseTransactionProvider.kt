package com.example.pos_boilerplate.core.cache.transaction

interface DatabaseTransactionProvider {
    suspend fun <T> runAsTransaction(block: suspend () -> T): T
    suspend fun ensureTransaction(block: suspend () -> Unit)
    fun isInTransaction(): Boolean
}