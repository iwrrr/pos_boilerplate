package com.example.pos_boilerplate.core.common.utils

import java.text.NumberFormat
import java.util.Locale

fun Long.toRupiah(): String {
    val formatIDR = NumberFormat.getCurrencyInstance(Locale("id", "ID")).apply {
        maximumFractionDigits = 0
        minimumFractionDigits = 0
    }

    return formatIDR.format(this)
}