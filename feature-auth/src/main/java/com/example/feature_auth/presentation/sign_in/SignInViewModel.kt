package com.example.feature_auth.presentation.sign_in

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.BaseViewModel
import com.example.core.util.InputLinter
import com.example.core.util.exhaustive
import com.example.core.util.result
import com.example.feature_auth.data.repository.AuthRepository
import com.example.feature_auth.presentation.common.item.InputFiled
import com.example.feature_auth.presentation.sign_in.item.UserSignInItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import javax.inject.Named

class SignInViewModel @AssistedInject constructor(
    @Assisted("signIn") private val onSignInClicked: () -> Unit,
    @Assisted("signUp") private val onSignUpClicked: () -> Unit,
    private val authRepository: AuthRepository,
    @Named("emailLinter") private val emailLinter: InputLinter,
    @Named("passwordLinter") private val passwordLinter: InputLinter
) : BaseViewModel<SignInUiEvent, SignInUiState>() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("signIn") onSignInClicked: () -> Unit,
            @Assisted("signUp") onSignUpClicked: () -> Unit
        ): SignInViewModel
    }

    override fun createInitialState(): SignInUiState {
        val user = UserSignInItem(email = InputFiled(""), password = InputFiled(""))
        return SignInUiState.WaitingUserData(
            user = user,
            isSignInEnabled = isSignInEnabled(user)
        )
    }

    override fun handleEvent(event: SignInUiEvent) {
        when (val currentState = _uiState.value) {
            is SignInUiState.FailedSignIn -> reduce(event, currentState)
            is SignInUiState.WaitingResponse -> throw IllegalStateException()
            is SignInUiState.WaitingUserData -> reduce(event, currentState)
        }.exhaustive
    }

    private fun reduce(event: SignInUiEvent, currentState: SignInUiState.FailedSignIn) {
        when (event) {
            is SignInUiEvent.SignIn -> signIn(user = currentState.user)
            is SignInUiEvent.NavigateToSignUp -> onSignUpClicked()
            is SignInUiEvent.UpdateUserEmail -> {
                val userInput = UserSignInItem(
                    email = getNewEmail(event.email),
                    password = currentState.user.password
                )
                _uiState.value = SignInUiState.FailedSignIn(
                    user = userInput,
                    cause = currentState.cause,
                    isSignInEnabled = isSignInEnabled(userInput)
                )
            }
            is SignInUiEvent.UpdateUserPassword -> {
                val userInput = UserSignInItem(
                    email = currentState.user.email,
                    password = getNewPassword(event.password)
                )
                _uiState.value = SignInUiState.FailedSignIn(
                    user = userInput,
                    cause = currentState.cause,
                    isSignInEnabled = isSignInEnabled(userInput)
                )
            }
        }.exhaustive
    }

    private fun reduce(event: SignInUiEvent, currentState: SignInUiState.WaitingUserData) {
        when (event) {
            is SignInUiEvent.SignIn -> signIn(user = currentState.user)
            is SignInUiEvent.NavigateToSignUp -> onSignUpClicked()
            is SignInUiEvent.UpdateUserEmail -> {
                val userInput = UserSignInItem(
                    email = getNewEmail(event.email),
                    password = currentState.user.password
                )
                _uiState.value = SignInUiState.WaitingUserData(
                    user = userInput,
                    isSignInEnabled = isSignInEnabled(userInput)
                )
            }
            is SignInUiEvent.UpdateUserPassword -> {
                val userInput = UserSignInItem(
                    email = currentState.user.email,
                    password = getNewPassword(event.password)
                )
                _uiState.value = SignInUiState.WaitingUserData(
                    user = userInput,
                    isSignInEnabled = isSignInEnabled(userInput)
                )
            }
        }.exhaustive
    }

    private fun isSignInEnabled(user: UserSignInItem): Boolean {
        return (user.email.error == null) and
                (user.email.value.isNotEmpty()) and

                (user.password.error == null) and
                (user.password.value.isNotEmpty())
    }

    private fun signIn(user: UserSignInItem) {
        viewModelScope.launch {
            authRepository.signIn(
                email = user.email.value.trim(),
                password = user.password.value.trim()
            ).result(
                onSuccess = { onSignInClicked() },
                onFailure = {
                    _uiState.value = SignInUiState.FailedSignIn(
                        user = user,
                        cause = it.error.localizedMessage,
                        isSignInEnabled = isSignInEnabled(user)
                    )
                }
            )

        }
    }

    private fun getNewEmail(email: String): InputFiled {
        return InputFiled(
            value = email,
            error = emailLinter.check(email.trim())
        )
    }

    private fun getNewPassword(password: String): InputFiled {
        return InputFiled(
            value = password,
            error = passwordLinter.check(password.trim())
        )
    }

}
