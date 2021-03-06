package com.example.feature_auth.presentation.sign_in

import com.example.core.presentation.UiState
import com.example.feature_auth.presentation.sign_in.item.UserSignInItem

sealed class SignInUiState(val user: UserSignInItem, val isSignInEnabled: Boolean) : UiState() {

    class WaitingUserData(user: UserSignInItem, isSignInEnabled: Boolean)
        : SignInUiState(user = user, isSignInEnabled = isSignInEnabled)

    class WaitingResponse(user: UserSignInItem)
        : SignInUiState(user = user, isSignInEnabled = false)

    class FailedSignIn(user: UserSignInItem, val cause: String?, isSignInEnabled: Boolean)
        : SignInUiState(user = user, isSignInEnabled = isSignInEnabled)

}