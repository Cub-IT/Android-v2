package com.example.feature_auth.presentation.sign_in.mvi

import com.example.core.presentation.UiState
import com.example.core.presentation.item.Reloadable
import com.example.feature_auth.presentation.sign_in.item.UserSignInItem

data class SignInUiState(
    val user: Reloadable<UserSignInItem>
) : UiState() {

    val isSignInEnabled: Boolean =
        (user.status is Reloadable.Status.Idle) and

        (user.value.email.error == null) and
        (user.value.email.value.isNotEmpty()) and

        (user.value.password.error == null) and
        (user.value.password.value.isNotEmpty())

}