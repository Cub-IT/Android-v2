package com.example.feature_auth.presentation.sign_in.mvi

import com.example.core.presentation.Actor
import com.example.core.util.readableCause
import com.example.core.util.result
import com.example.feature_auth.data.repository.AuthRepository
import com.example.feature_auth.presentation.sign_in.item.UserSignInItem
import javax.inject.Inject

class SignInMiddleware @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(user: UserSignInItem, actor: Actor<SignInUiEvent>) {
        authRepository.signIn(
            email = user.email.value.trim(),
            password = user.password.value.trim()
        ).result(
            onSuccess = { actor.handleEvent(event = SignInUiEvent.SignInSuccessful) },
            onFailure = {
                actor.handleEvent(
                    event = SignInUiEvent.SignInError(cause = it.error.readableCause())
                )
            }
        )
    }
}