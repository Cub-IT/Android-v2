package com.example.core.util

import okio.IOException
import retrofit2.HttpException

fun Exception.readableCause(): String? {
    return when (this) {
        is HttpException -> when(this.code()) {
            401 -> "Your are not authorized so you can't proceed. Authorize and try again"
            403 -> "Your input is incorrect so you can't proceed. Change it and try again"
            500 -> "Internal server error. Your input may not be correct"
            else -> "Internal server error occurred with code ${this.code()}. Your input may not be correct"
        }
        is IOException -> "Couldn't reach server. Check your internet connection"
        else -> this.localizedMessage
    }
}