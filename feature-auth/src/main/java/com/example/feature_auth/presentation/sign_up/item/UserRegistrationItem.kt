package com.example.feature_auth.presentation.sign_up.item

import com.example.feature_auth.presentation.common.item.InputFiled

data class UserRegistrationItem(
    val firstName: InputFiled,
    val lastName: InputFiled,
    val email: InputFiled,
    val password: InputFiled,
)