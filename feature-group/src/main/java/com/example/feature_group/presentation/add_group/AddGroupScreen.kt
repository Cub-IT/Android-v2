package com.example.feature_group.presentation.add_group

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core.presentation.composable.ErrorMessage
import com.example.feature_group.presentation.add_group.composable.Fields
import com.example.feature_group.presentation.common.composable.IconAvatar
import com.example.feature_group.presentation.group_list.GroupListUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGroupScreen(
    viewModel: AddGroupViewModel
) {
    val uiState by viewModel.uiState

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Create group")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.handleEvent(event = AddGroupUiEvent.BackClicked)
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    FilledTonalButton(
                        onClick = { viewModel.handleEvent(event = AddGroupUiEvent.CreateGroup) },
                        enabled = uiState.isCreationEnabled
                    ) {
                        Text(text = "Create")
                    }
                }
            )
        }
    ) {
        when (uiState) {
            is AddGroupUiState.FailedCreation,
            is AddGroupUiState.WaitingGroupData -> {
                Box(Modifier.fillMaxSize()) {
                    if (uiState is AddGroupUiState.FailedCreation) {
                        ErrorMessage(
                            errorCause = (uiState as AddGroupUiState.FailedCreation).cause,
                            modifier = Modifier
                                .padding(16.dp)
                                .padding(it)
                        )
                    }

                    Fields(
                        uiState = uiState,
                        viewModel = viewModel,
                    )
                }
            }
            is AddGroupUiState.WaitingResponse -> {
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