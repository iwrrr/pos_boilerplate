package com.example.pos_boilerplate.di

import com.example.pos_boilerplate.features.cart.CartViewModel
import com.example.pos_boilerplate.features.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::CartViewModel)
}