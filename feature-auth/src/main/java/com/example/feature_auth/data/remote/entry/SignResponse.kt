package com.example.feature_auth.data.remote.entry

data class SignResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val participants: List<Any>,
    val posts: List<Any>
)