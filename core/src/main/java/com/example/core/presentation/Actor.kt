package com.example.core.presentation

interface Actor<E : UiEvent> {

    fun handleEvent(event: E)

}