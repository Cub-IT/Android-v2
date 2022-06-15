package com.example.feature_auth.presentation.common.item

import androidx.annotation.StringRes

data class InputFiled(
    val value: String,
    @StringRes val error: Int? = null,
)
