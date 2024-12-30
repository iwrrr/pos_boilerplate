package com.example.pos_boilerplate.core.data.di

import com.example.pos_boilerplate.core.data.repository.CartRepositoryImpl
import com.example.pos_boilerplate.core.data.repository.ProductRepositoryImpl
import com.example.pos_boilerplate.core.domain.repository.CartRepository
import com.example.pos_boilerplate.core.domain.repository.ProductRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::ProductRepositoryImpl) { bind<ProductRepository>() }
    singleOf(::CartRepositoryImpl) { bind<CartRepository>() }
}