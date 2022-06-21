package com.example.feature_group.presentation.group

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core.presentation.composable.ErrorMessage
import com.example.feature_group.presentation.common.composable.IconAvatar
import com.example.feature_group.presentation.group.composable.TaskList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreen(
    viewModel: GroupViewModel
) {
    val uiState by viewModel.uiState

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Name of the group")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.handleEvent(event = GroupUiEvent.BackClicked)
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.handleEvent(event = GroupUiEvent.UserAvatarClicked)
                    }) {
                        IconAvatar(color = Color(0xFF3B79E8), size = 40.dp)
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it).fillMaxSize()) {
            if (uiState is GroupUiState.Loading) {
                LinearProgressIndicator()
            }

            if (uiState is GroupUiState.ErrorLoadingTasks) {
                ErrorMessage(
                    errorCause = (uiState as GroupUiState.ErrorLoadingTasks).cause,
                    modifier = Modifier.padding(16.dp)
                )
            }

            TaskList(
                group = uiState.group,
                tasks = uiState.posts
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.handleEvent(event = GroupUiEvent.LoadGroup)
    }
}