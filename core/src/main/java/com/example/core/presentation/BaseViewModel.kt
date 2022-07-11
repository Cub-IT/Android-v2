package com.example.core.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<E : UiEvent, S : UiState> : ViewModel(), Actor<E> {

    protected val _uiState: MutableState<S> = mutableStateOf(createInitialState())
    val uiState: State<S> = _uiState

    protected abstract fun createInitialState(): S

    abstract override fun handleEvent(event: E)

}