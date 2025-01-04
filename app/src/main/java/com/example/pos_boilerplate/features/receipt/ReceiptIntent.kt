package com.example.pos_boilerplate.features.receipt

import com.example.pos_boilerplate.core.common.viewmodel.Intent

sealed class ReceiptIntent : Intent {
    data class GetReceipt(val receiptId: String) : ReceiptIntent()
}