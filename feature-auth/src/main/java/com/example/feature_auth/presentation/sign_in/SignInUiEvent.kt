package com.example.feature_auth.presentation.sign_in

import com.example.core.presentation.UiEvent
import com.example.feature_auth.presentation.sign_in.item.InputFiled
import com.example.feature_auth.presentation.sign_in.item.UserSignInItem

sealed class SignInUiEvent : UiEvent() {
    class UpdateUserEmail(val email: String) : SignInUiEvent()
    class UpdateUserPassword(val password: String) : SignInUiEvent()
    object SignIn : SignInUiEvent()
    object NavigateToSignUp : SignInUiEvent()
}