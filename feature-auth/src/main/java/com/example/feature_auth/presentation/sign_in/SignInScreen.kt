package com.example.feature_auth.presentation.sign_in

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.presentation.composable.ErrorMessage
import com.example.feature_auth.R
import com.example.feature_auth.presentation.common.composable.BottomButtons
import com.example.feature_auth.presentation.sign_in.composable.Fields

@Composable
fun SingInScreen(
    viewModel: SignInViewModel
) {
    val uiState by viewModel.uiState

    when (uiState) {
        is SignInUiState.FailedSignIn,
        is SignInUiState.WaitingUserData -> {
            Box(Modifier.fillMaxSize()) {
                if (uiState is SignInUiState.FailedSignIn) {
                    ErrorMessage(
                        errorCause = (uiState as SignInUiState.FailedSignIn).cause,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Fields(
                    uiState = uiState,
                    viewModel = viewModel,
                )

                BottomButtons(
                    positiveButtonText = stringResource(R.string.sign_in),
                    onPositiveButtonClick = { viewModel.handleEvent(event = SignInUiEvent.SignIn) },
                    negativeButtonText = stringResource(R.string.sign_up),
                    onNegativeButtonClick = { viewModel.handleEvent(event = SignInUiEvent.NavigateToSignUp) },
                    isPositiveButtonEnabled = uiState.isSignInEnabled,
                    isNegativeButtonVisible = true,
                )
            }
        }
        is SignInUiState.WaitingResponse -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}