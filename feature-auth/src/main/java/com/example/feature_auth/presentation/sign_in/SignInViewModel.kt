package com.example.feature_auth.presentation.sign_in

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.core.data.repository.AuthRepository
import com.example.core.presentation.BaseViewModel
import com.example.core.util.exhaustive
import com.example.feature_auth.presentation.sign_in.item.UserSignInItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class SignInViewModel @AssistedInject constructor(
    @Assisted("signIn") private val onSignInClicked: () -> Unit,
    @Assisted("signUp") private val onSignUpClicked: () -> Unit,
    private val authRepository: AuthRepository
) : BaseViewModel<SignInUiEvent, SignInUiState>() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("signIn") onSignInClicked: () -> Unit,
            @Assisted("signUp") onSignUpClicked: () -> Unit
        ): SignInViewModel
    }

    override fun createInitialState(): SignInUiState {
        val user = UserSignInItem(email = "", password = "")
        return SignInUiState.WaitingUserData(
            user = user,
            isSignInEnabled = isSignInEnabled(user)
        )
    }

    override fun handleEvent(event: SignInUiEvent) {
        when (val currentState = _uiState.value) {
            is SignInUiState.FailedSignIn -> reduce(event, currentState)
            is SignInUiState.WaitingResponse -> reduce(event, currentState)
            is SignInUiState.WaitingUserData -> reduce(event, currentState)
        }.exhaustive
    }

    private fun reduce(event: SignInUiEvent, currentState: SignInUiState.FailedSignIn) {
        when (event) {
            is SignInUiEvent.SignIn -> signIn(user = currentState.user)
            is SignInUiEvent.NavigateToSignUp -> onSignUpClicked()
            is SignInUiEvent.UpdateUserLogInData -> {
                _uiState.value = SignInUiState.FailedSignIn(
                    user = event.user,
                    cause = currentState.cause,
                    isSignInEnabled = isSignInEnabled(event.user)
                )
            }
        }.exhaustive
    }

    private fun reduce(event: SignInUiEvent, currentState: SignInUiState.WaitingResponse) {
        when (event) {
            is SignInUiEvent.SignIn,
            is SignInUiEvent.NavigateToSignUp,
            is SignInUiEvent.UpdateUserLogInData -> throw IllegalStateException()
        }.exhaustive
    }

    private fun reduce(event: SignInUiEvent, currentState: SignInUiState.WaitingUserData) {
        when (event) {
            is SignInUiEvent.SignIn -> signIn(user = currentState.user)
            is SignInUiEvent.NavigateToSignUp -> onSignUpClicked()
            is SignInUiEvent.UpdateUserLogInData -> {
                _uiState.value = SignInUiState.WaitingUserData(
                    user = event.user,
                    isSignInEnabled = isSignInEnabled(event.user)
                )
            }
        }.exhaustive
    }

    private fun isSignInEnabled(user: UserSignInItem): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(user.email).matches()
                && user.password.isNotBlank()
    }

    private fun signIn(user: UserSignInItem) {
        viewModelScope.launch {
            authRepository.signIn(
                email = user.email,
                password = user.password
            )
            onSignInClicked()
        }
    }

}
