package com.example.feature_auth.presentation.sign_in.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.core.presentation.theme.Typography
import com.example.feature_auth.presentation.sign_in.item.InputFiled

@Composable
fun UndefinedOutlinedTextField(
    field: InputFiled,
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    onValueChange: (newValue: String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = field.value,
            onValueChange = onValueChange,
            isError =  field.error != null,
            singleLine = true,
            keyboardOptions = keyboardOptions,
            modifier = modifier.fillMaxWidth(),
            label = { Text(text = stringResource(label)) },
            textStyle = Typography.bodyLarge
        )
        if (field.error != null) {
            Text(
                text = stringResource(field.error),
                style = Typography.labelMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }

}