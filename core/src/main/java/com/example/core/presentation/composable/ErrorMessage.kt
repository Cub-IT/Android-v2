package com.example.core.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.core.R
import com.example.core.presentation.theme.Typography

@Composable
fun ErrorMessage(
    errorCause: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.error_occurred),
            style = Typography.headlineMedium
        )
        Text(
            text = errorCause ?: stringResource(R.string.default_error_cause_message),
            style = Typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}