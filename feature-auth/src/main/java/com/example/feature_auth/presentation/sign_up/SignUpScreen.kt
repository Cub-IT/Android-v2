package com.example.feature_auth.presentation.sign_up

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.presentation.composable.ErrorMessage
import com.example.feature_auth.R
import com.example.feature_auth.presentation.common.composable.BottomButtons
import com.example.feature_auth.presentation.sign_in.SignInUiEvent
import com.example.feature_auth.presentation.sign_in.SignInUiState
import com.example.feature_auth.presentation.sign_up.composable.Fields

@Composable
fun SingUpScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    Box(Modifier.fillMaxSize()) {
        if (uiState is SignUpUiState.WaitingResponse) {
            CircularProgressIndicator(modifier = Modifier.padding(32.dp).align(Alignment.TopCenter))
        }

        if (uiState is SignUpUiState.FailedSignUp) {
            ErrorMessage(
                errorCause = (uiState as SignUpUiState.FailedSignUp).cause,
                modifier = Modifier.padding(16.dp)
            )
        }

        Fields(
            uiState = uiState,
            viewModel = viewModel,
        )

        BottomButtons(
            positiveButtonText = stringResource(R.string.sign_up),
            onPositiveButtonClick = { viewModel.handleEvent(event = SignUpUiEvent.SignUp) },
            negativeButtonText = stringResource(R.string.cancel),
            onNegativeButtonClick = { viewModel.handleEvent(event = SignUpUiEvent.NavigateToSignIn) },
            isPositiveButtonEnabled = uiState.isSignUpEnabled,
            isNegativeButtonVisible = true,
        )
    }
}