package com.example.pos_boilerplate.features.receipt

import androidx.lifecycle.viewModelScope
import com.example.pos_boilerplate.core.common.state.Async
import com.example.pos_boilerplate.core.common.viewmodel.Intent
import com.example.pos_boilerplate.core.common.viewmodel.ViewModelState
import com.example.pos_boilerplate.core.domain.model.Receipt
import com.example.pos_boilerplate.core.domain.model.ReceiptItem
import com.example.pos_boilerplate.core.domain.repository.ReceiptRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReceiptViewModel(
    private val receiptRepository: ReceiptRepository
) : ViewModelState<ReceiptState, ReceiptIntent>(ReceiptState()) {

    override fun sendIntent(intent: Intent) {
        when (intent) {
            is ReceiptIntent.GetReceipt -> {
                getReceipt(intent.receiptId)
            }
        }
    }

    private fun getReceipt(receiptId: String) {
        viewModelScope.launch {
            receiptRepository.getReceipt(receiptId)
                .stateIn(this)
                .collectLatest {
                    update {
                        copy(asyncInvoice = it)
                    }

                    if (it is Async.Success) {
                        getSummary(it.data)
                    }
                }
        }
    }

    private fun getSummary(data: Receipt) {
        val subtotal = data.items.sumOf(ReceiptItem::subtotal)
        val tax = (data.items.sumOf(ReceiptItem::subtotal) * 0.12).toLong()
        val total = subtotal + tax

        update {
            copy(
                subtotal = subtotal,
                tax = tax,
                total = total,
            )
        }
    }
}