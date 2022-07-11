package com.example.feature_auth.presentation.sign_in.mvi

import com.example.core.presentation.UiEvent

sealed class SignInUiEvent : UiEvent() {
    class UpdateUserEmail(val email: String) : SignInUiEvent()
    class UpdateUserPassword(val password: String) : SignInUiEvent()

    object SignIn : SignInUiEvent()
    object NavigateToSignUp : SignInUiEvent()

    object SignInSuccessful : SignInUiEvent()
    data class SignInError(val cause: String?) : SignInUiEvent()
}