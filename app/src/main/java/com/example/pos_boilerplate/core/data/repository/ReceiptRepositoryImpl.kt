package com.example.pos_boilerplate.core.data.repository

import com.example.pos_boilerplate.core.cache.dao.CartDao
import com.example.pos_boilerplate.core.cache.dao.ReceiptDao
import com.example.pos_boilerplate.core.cache.model.CartEntity
import com.example.pos_boilerplate.core.cache.model.ReceiptEntity
import com.example.pos_boilerplate.core.cache.model.ReceiptItemEntity
import com.example.pos_boilerplate.core.cache.model.ReceiptWithItemsEntity
import com.example.pos_boilerplate.core.cache.transaction.DatabaseTransactionProvider
import com.example.pos_boilerplate.core.common.repository.Repository
import com.example.pos_boilerplate.core.common.state.Async
import com.example.pos_boilerplate.core.data.mapper.toDomain
import com.example.pos_boilerplate.core.domain.model.Checkout
import com.example.pos_boilerplate.core.domain.model.Receipt
import com.example.pos_boilerplate.core.domain.repository.ReceiptRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class ReceiptRepositoryImpl(
    private val receiptDao: ReceiptDao,
    private val cartDao: CartDao,
    private val transactionProvider: DatabaseTransactionProvider
) : Repository(), ReceiptRepository {

    override fun checkout(data: Checkout): Flow<Async<String>> = reduce {
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
            receiptEntity.id
        }
    }

    override fun getReceipt(receiptId: String): Flow<Async<Receipt>> = flow {
        emit(Async.Loading)
        try {
            receiptDao.getReceiptById(receiptId)
                .map { it?.toDomain() }
                .collect {
                    if (it == null) {
                        emit(Async.Failure(Throwable("Receipt not found")))
                    } else {
                        emit(Async.Success(it))
                    }
                }
        } catch (e: Exception) {
            emit(Async.Failure(e))
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