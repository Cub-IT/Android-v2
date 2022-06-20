package com.example.feature_auth.data.remote.api

import com.example.core.util.Result
import com.example.feature_auth.data.remote.entry.SignInRequestEntry
import com.example.feature_auth.data.remote.entry.SignResponseEntry
import com.example.feature_auth.data.remote.entry.SignUpRequestEntry
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun signIn(
        @Body signInRequestEntry: SignInRequestEntry
    ): Result<SignResponseEntry, Exception>

    @POST("new")
    suspend fun signUp(
        @Body signUpRequestEntry: SignUpRequestEntry
    ): Result<SignResponseEntry, Exception>

}