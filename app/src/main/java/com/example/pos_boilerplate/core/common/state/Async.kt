package com.example.pos_boilerplate.core.common.state

import androidx.compose.runtime.Immutable

sealed class Async<out T> {
    data object Default : Async<Nothing>()
    data object Loading : Async<Nothing>()

    @Immutable
    data class Success<T>(val data: T) : Async<T>()

    @Immutable
    data class Failure(val throwable: Throwable) : Async<Nothing>()
}