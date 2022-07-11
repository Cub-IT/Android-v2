package com.example.feature_auth.presentation.sign_in

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.BaseViewModel
import com.example.core.util.InputLinter
import com.example.core.presentation.item.InputFiled
import com.example.core.presentation.item.Reloadable
import com.example.feature_auth.presentation.sign_in.item.UserSignInItem
import com.example.feature_auth.presentation.sign_in.mvi.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import javax.inject.Named

class SignInViewModel @AssistedInject constructor(
    @Assisted("signIn") private val onSignInClicked: () -> Unit,
    @Assisted("signUp") private val onSignUpClicked: () -> Unit,
    private val signInMiddleware: SignInMiddleware,
    private val signInReducer: SignInReducer,
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
        return SignInUiState(user = Reloadable(
            value = UserSignInItem(email = InputFiled(""), password = InputFiled("")),
            status = Reloadable.Status.Idle
        ))
    }

    override fun handleEvent(event: SignInUiEvent) {
        when (event) {
            is SignInUiEvent.NavigateToSignUp -> onSignUpClicked()
            is SignInUiEvent.SignIn -> {
                viewModelScope.launch {
                    signInMiddleware(user = uiState.value.user.value, actor = this@SignInViewModel)
                }
                signInReducer.reduce(
                    internalEvent = SignInUiInternalEvent.ShowLoading,
                    currentState = uiState.value
                )
            }
            is SignInUiEvent.SignInSuccessful -> onSignInClicked()
            is SignInUiEvent.UpdateUserEmail -> signInReducer.reduce(
                internalEvent = SignInUiInternalEvent.UpdateUserEmail(
                    email = getNewEmail(event.email)
                ),
                currentState = uiState.value
            )
            is SignInUiEvent.UpdateUserPassword -> signInReducer.reduce(
                internalEvent = SignInUiInternalEvent.UpdateUserPassword(
                    password = getNewPassword(event.password)
                ),
                currentState = uiState.value
            )
            is SignInUiEvent.SignInError -> signInReducer.reduce(
                internalEvent = SignInUiInternalEvent.ShowError(
                    error = event.cause
                ),
                currentState = uiState.value
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
