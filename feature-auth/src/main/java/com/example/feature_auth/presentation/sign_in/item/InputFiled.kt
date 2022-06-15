package com.example.feature_auth.presentation.sign_in.item

import androidx.annotation.StringRes

data class InputFiled(
    val value: String,
    @StringRes val error: Int? = null,
)
