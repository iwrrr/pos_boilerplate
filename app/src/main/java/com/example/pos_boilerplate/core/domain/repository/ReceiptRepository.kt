package com.example.pos_boilerplate.core.domain.repository

import com.example.pos_boilerplate.core.common.state.Async
import com.example.pos_boilerplate.core.domain.model.Checkout
import com.example.pos_boilerplate.core.domain.model.Receipt
import kotlinx.coroutines.flow.Flow

interface ReceiptRepository {
    fun checkout(data: Checkout): Flow<Async<String>>
    fun getReceipt(receiptId: String): Flow<Async<Receipt>>
}