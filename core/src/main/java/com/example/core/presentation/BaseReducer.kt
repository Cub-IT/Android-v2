package com.example.core.presentation

interface BaseReducer<E : UiInternalEvent, S : UiState> {

    // returns a new state
    fun reduce(internalEvent: E, currentState: S): S

}