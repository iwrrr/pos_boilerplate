package com.example.pos_boilerplate.core.domain.repository

import com.example.pos_boilerplate.core.common.state.Async
import com.example.pos_boilerplate.core.domain.model.Checkout
import kotlinx.coroutines.flow.Flow

interface ReceiptRepository {
    fun checkout(data: Checkout): Flow<Async<Unit>>
}