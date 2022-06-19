package com.example.feature_auth.presentation.sign_in.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.feature_auth.R
import com.example.core.presentation.composable.UndefinedOutlinedTextField
import com.example.feature_auth.presentation.sign_in.SignInUiEvent
import com.example.feature_auth.presentation.sign_in.SignInUiState
import com.example.feature_auth.presentation.sign_in.SignInViewModel

@Composable
fun Fields(
    uiState: SignInUiState,
    viewModel: SignInViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // user email TextField
        UndefinedOutlinedTextField(
            field = uiState.user.email,
            label = R.string.user_email,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = { newValue ->
                viewModel.handleEvent(event = SignInUiEvent.UpdateUserEmail(newValue))
            }
        )

        Spacer(modifier = Modifier.padding(16.dp))

        // user password TextField
        UndefinedOutlinedTextField(
            field = uiState.user.password,
            label = R.string.user_password,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            onValueChange = { newValue ->
                viewModel.handleEvent(event = SignInUiEvent.UpdateUserPassword(newValue))
            }
        )
    }
}