package com.example.pos_boilerplate.core.common.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class ViewModelState<STATE, INTENT>(initialState: STATE) : ViewModel() {
    private val _uiState: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val uiState: StateFlow<STATE> get() = _uiState

    abstract fun sendIntent(intent: Intent)

    protected fun update(block: STATE.() -> STATE) {
        _uiState.update(block)
    }
}