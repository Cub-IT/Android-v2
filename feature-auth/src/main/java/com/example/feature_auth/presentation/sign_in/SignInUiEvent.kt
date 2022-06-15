package com.example.feature_auth.presentation.sign_in

import com.example.core.presentation.UiEvent

sealed class SignInUiEvent : UiEvent() {
    class UpdateUserEmail(val email: String) : SignInUiEvent()
    class UpdateUserPassword(val password: String) : SignInUiEvent()
    object SignIn : SignInUiEvent()
    object NavigateToSignUp : SignInUiEvent()
}