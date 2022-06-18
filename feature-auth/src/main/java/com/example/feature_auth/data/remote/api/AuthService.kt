package com.example.feature_auth.data.remote.api

import com.example.core.util.Result
import com.example.feature_auth.data.remote.entry.SignInRequestEntry
import com.example.feature_auth.data.remote.entry.SignResponseEntry
import com.example.feature_auth.data.remote.entry.SignUpRequestEntry
import retrofit2.Retrofit
import javax.inject.Inject

class AuthService @Inject constructor(
    private val retrofit: Retrofit
) {

    private val authApi: AuthApi by lazy { retrofit.create(AuthApi::class.java) }

    suspend fun signIn(email: String, password: String): Result<SignResponseEntry, Exception> {
        val signInRequestEntry = SignInRequestEntry(
            email = email,
            password = password
        )
        return authApi.signIn(signInRequestEntry = signInRequestEntry)
    }

    suspend fun signUp(firstName: String, lastName: String, email: String, password: String): Result<SignResponseEntry, Exception> {
        val signUpRequestEntry = SignUpRequestEntry(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password
        )
        return authApi.signUp(signUpRequestEntry = signUpRequestEntry)
    }
}