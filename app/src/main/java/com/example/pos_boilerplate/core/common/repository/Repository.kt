package com.example.pos_boilerplate.core.common.repository

import com.example.pos_boilerplate.core.common.state.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

abstract class Repository {

    inline fun <T> reduce(crossinline action: suspend () -> T): Flow<Async<T>> = flow {
        emit(Async.Loading)
        try {
            emit(Async.Success(action()))
        } catch (e: Exception) {
            Timber.e("Error: $e")
            emit(Async.Failure(e))
        }
    }
}