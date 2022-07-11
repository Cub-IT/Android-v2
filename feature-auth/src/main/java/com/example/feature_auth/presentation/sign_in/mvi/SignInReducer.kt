package com.example.feature_auth.presentation.sign_in.mvi

import com.example.core.presentation.BaseReducer
import com.example.core.presentation.item.Reloadable
import com.example.feature_auth.presentation.sign_in.item.UserSignInItem
import javax.inject.Inject

class SignInReducer @Inject constructor() : BaseReducer<SignInUiInternalEvent, SignInUiState> {

    override fun reduce(
        internalEvent: SignInUiInternalEvent,
        currentState: SignInUiState
    ): SignInUiState {
        return when (internalEvent) {
            is SignInUiInternalEvent.ShowError -> SignInUiState(user = Reloadable(
                value = currentState.user.value,
                status = Reloadable.Status.Error(reason = internalEvent.error)
            ))

            is SignInUiInternalEvent.ShowLoading -> SignInUiState(user = Reloadable(
                value = currentState.user.value,
                status = Reloadable.Status.Loading
            ))

            is SignInUiInternalEvent.UpdateUserEmail -> SignInUiState(user = Reloadable(
                value = UserSignInItem(
                    email = internalEvent.email,
                    password = currentState.user.value.password
                ),
                status = currentState.user.status
            ))

            is SignInUiInternalEvent.UpdateUserPassword -> SignInUiState(user = Reloadable(
                value = UserSignInItem(
                    email = currentState.user.value.email,
                    password = internalEvent.password
                ),
                status = currentState.user.status
            ))
        }
    }

}
