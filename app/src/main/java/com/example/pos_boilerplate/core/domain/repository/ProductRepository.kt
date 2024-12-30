package com.example.pos_boilerplate.core.domain.repository

import com.example.pos_boilerplate.core.common.state.Async
import com.example.pos_boilerplate.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductList(): Flow<Async<List<Product>>>
}