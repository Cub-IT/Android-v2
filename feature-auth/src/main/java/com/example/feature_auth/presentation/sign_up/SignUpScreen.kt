package com.example.feature_auth.presentation.sign_up

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.presentation.theme.Typography
import com.example.feature_auth.R
import com.example.feature_auth.presentation.common.composable.BottomButtons
import com.example.feature_auth.presentation.sign_up.composable.Fields

@Composable
fun SingUpScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    when (uiState) {
        is SignUpUiState.FailedSignUp,
        is SignUpUiState.WaitingUserData -> {
            Box(Modifier.fillMaxSize()) {
                if (uiState is SignUpUiState.FailedSignUp) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.error_occurred),
                            style = Typography.headlineMedium
                        )
                        Text(
                            text = (uiState as SignUpUiState.FailedSignUp).cause
                                ?: stringResource(R.string.default_error_header_message),
                            style = Typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
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
        is SignUpUiState.WaitingResponse -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}