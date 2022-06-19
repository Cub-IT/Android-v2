package com.example.feature_group.presentation.join_group

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.presentation.composable.ErrorMessage
import com.example.core.presentation.composable.UndefinedOutlinedTextField
import com.example.feature_group.R
import com.example.feature_group.presentation.add_group.AddGroupUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinGroupScreen(
    viewModel: JoinGroupViewModel
) {
    val uiState by viewModel.uiState

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Join group")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.handleEvent(event = JoinGroupUiEvent.BackClicked)
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    FilledTonalButton(
                        onClick = { viewModel.handleEvent(event = JoinGroupUiEvent.JoinGroup) },
                        enabled = uiState.isJoiningEnabled
                    ) {
                        Text(text = "Create")
                    }
                }
            )
        }
    ) {
        when (uiState) {
            is JoinGroupUiState.FailedCreation,
            is JoinGroupUiState.WaitingGroupData -> {
                Box(Modifier.fillMaxSize()) {
                    if (uiState is JoinGroupUiState.FailedCreation) {
                        ErrorMessage(
                            errorCause = (uiState as JoinGroupUiState.FailedCreation).cause,
                            modifier = Modifier
                                .padding(16.dp)
                                .padding(it)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Asc your teacher for the group code, then enter it here.")
                        UndefinedOutlinedTextField(
                            field = uiState.groupCode,
                            label = R.string.group_code,
                            onValueChange = { newValue ->
                                viewModel.handleEvent(event = JoinGroupUiEvent.UpdateGroupCode(newValue))
                            }
                        )
                    }
                }
            }
            is JoinGroupUiState.WaitingResponse -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}