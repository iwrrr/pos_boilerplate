package com.example.pos_boilerplate.features.home

import com.example.pos_boilerplate.core.common.viewmodel.Intent
import com.example.pos_boilerplate.core.domain.model.Product

sealed class HomeIntent : Intent {
    data object GetProductList : HomeIntent()
    data object GetTotalCart : HomeIntent()
    data class AddToCart(val item: Product) : HomeIntent()
}