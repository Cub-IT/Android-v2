package com.example.feature_auth.data.remote.entry

import com.google.gson.annotations.SerializedName

data class SignInRequestEntry(
    @SerializedName("login")
    val email: String,
    val password: String
)
