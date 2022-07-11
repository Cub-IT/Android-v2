package com.example.core.presentation.item

data class Reloadable<out T : Any?>(val value: T, val status: Status) {

    sealed class Status {
        object Idle: Status()
        object Loading : Status()
        data class Error(val reason: String?) : Status()
    }
}
