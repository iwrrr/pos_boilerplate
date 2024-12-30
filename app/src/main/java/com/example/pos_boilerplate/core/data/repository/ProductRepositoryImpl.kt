package com.example.pos_boilerplate.core.data.repository

import com.example.pos_boilerplate.core.common.state.Async
import com.example.pos_boilerplate.core.domain.model.Product
import com.example.pos_boilerplate.core.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class ProductRepositoryImpl : ProductRepository {

    override fun getProductList(): Flow<Async<List<Product>>> = flow {
        val productList = (1..10).map {
            Product(
                id = it,
                name = "Product $it",
                image = "https://fakeimg.pl/600x400/f2f2f2/909090?text=+",
                price = Random.nextLong(1_000, 10_000)
            )
        }

        emit(Async.Success(productList))
    }
}