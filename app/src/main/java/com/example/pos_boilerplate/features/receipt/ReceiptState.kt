package com.example.pos_boilerplate.features.receipt

import com.example.pos_boilerplate.core.common.state.Async
import com.example.pos_boilerplate.core.domain.model.Receipt

data class ReceiptState(
    val asyncInvoice: Async<Receipt> = Async.Default,
    val subtotal: Long = 0L,
    val tax: Long = 0L,
    val total: Long = 0L,
)