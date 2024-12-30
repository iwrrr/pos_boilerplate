package com.example.pos_boilerplate.core.data.repository

import com.example.pos_boilerplate.core.cache.dao.CartDao
import com.example.pos_boilerplate.core.cache.dao.ReceiptDao
import com.example.pos_boilerplate.core.cache.model.ReceiptEntity
import com.example.pos_boilerplate.core.cache.model.ReceiptItemEntity
import com.example.pos_boilerplate.core.cache.transaction.DatabaseTransactionProvider
import com.example.pos_boilerplate.core.common.repository.Repository
import com.example.pos_boilerplate.core.common.state.Async
import com.example.pos_boilerplate.core.domain.model.Checkout
import com.example.pos_boilerplate.core.domain.repository.ReceiptRepository
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class ReceiptRepositoryImpl(
    private val receiptDao: ReceiptDao,
    private val cartDao: CartDao,
    private val transactionProvider: DatabaseTransactionProvider
) : Repository(), ReceiptRepository {

    override fun checkout(data: Checkout): Flow<Async<Unit>> = reduce {
        transactionProvider.runAsTransaction {
            val receiptEntity = ReceiptEntity(
                id = generateReceiptId(),
                totalAmount = data.totalAmount,
                paymentMethod = data.paymentMethod.id,
                customerName = data.customerName,
                note = data.note,
            )

            val receiptItems = data.items.map {
                ReceiptItemEntity(
                    receiptId = receiptEntity.id,
                    productName = it.productName,
                    quantity = it.quantity,
                    subtotal = it.subtotal
                )
            }

            receiptDao.checkout(receiptEntity, receiptItems)
            cartDao.clearCart()
        }
    }

    private fun generateReceiptId(): String {
        val timestamp = SimpleDateFormat(
            "yyyyMMddHHmmss",
            Locale.getDefault()
        ).format(Date())

        val random = Random.nextInt(1000, 9999)
        return "RCP-$timestamp-$random"
    }
}