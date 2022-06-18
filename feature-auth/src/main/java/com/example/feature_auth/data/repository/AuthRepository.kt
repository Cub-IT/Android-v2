package com.example.feature_auth.data.repository

import com.example.core.data.local.UserSource
import com.example.core.util.Result
import com.example.core.util.onFailure
import com.example.feature_auth.data.remote.api.AuthService
import com.example.feature_auth.data.remote.entry.toUserItem
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val userSource: UserSource,
    private val authService: AuthService
) {

    suspend fun signIn(email: String, password: String): Result<Unit, Exception> {
        val userItem = authService.signIn(email = email, password = password)
            .onFailure { return it }
            .toUserItem()

        userSource.saveUser(userItem)
        return Result.Success(Unit)
    }

    suspend fun signUp(firstName: String, lastName: String, email: String, password: String): Result<Unit, Exception> {
        val userItem = authService.signUp(firstName = firstName, lastName = lastName, email = email, password = password)
            .onFailure { return it }
            .toUserItem()

        userSource.saveUser(userItem)
        return Result.Success(Unit)
    }

}