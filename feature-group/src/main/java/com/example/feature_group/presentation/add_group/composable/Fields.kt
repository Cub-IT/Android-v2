package com.example.feature_group.presentation.add_group.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.core.presentation.composable.UndefinedOutlinedTextField
import com.example.feature_group.presentation.add_group.AddGroupUiEvent
import com.example.feature_group.presentation.add_group.AddGroupUiState
import com.example.feature_group.presentation.add_group.AddGroupViewModel
import com.example.feature_group.R

@Composable
fun Fields(
    uiState: AddGroupUiState,
    viewModel: AddGroupViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // group name TextField
        UndefinedOutlinedTextField(
            field = uiState.group.name,
            label = R.string.group_name,
            onValueChange = { newValue ->
                viewModel.handleEvent(event = AddGroupUiEvent.UpdateGroupName(newValue))
            }
        )

        Spacer(modifier = Modifier.padding(16.dp))

        // group description TextField
        UndefinedOutlinedTextField(
            field = uiState.group.description,
            label = R.string.group_description,
            singleLine = false,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.None),
            onValueChange = { newValue ->
                viewModel.handleEvent(event = AddGroupUiEvent.UpdateGroupDescription(newValue))
            }
        )
    }
}