package com.example.feature_auth.presentation.sign_in.mvi

import com.example.core.presentation.UiInternalEvent
import com.example.core.presentation.item.InputFiled

sealed class SignInUiInternalEvent : UiInternalEvent() {
    data class UpdateUserEmail(val email: InputFiled) : SignInUiInternalEvent()
    data class UpdateUserPassword(val password: InputFiled) : SignInUiInternalEvent()
    object ShowLoading : SignInUiInternalEvent()
    data class ShowError(val error: String?): SignInUiInternalEvent()
}
