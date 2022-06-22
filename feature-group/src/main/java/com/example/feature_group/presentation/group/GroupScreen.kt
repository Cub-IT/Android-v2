package com.example.feature_group.presentation.group

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
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
    Log.i("TAG1", "GroupScreen: state: ${uiState.posts}")

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = uiState.group.name)
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
                    IconButton(onClick = {
                        viewModel.handleEvent(event = GroupUiEvent.LoadGroup)
                    }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            if (uiState.isOwner) {
                FloatingActionButton(onClick = {
                    viewModel.handleEvent(event = GroupUiEvent.AddPostClicked)
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            if (uiState is GroupUiState.Loading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            if (uiState is GroupUiState.ErrorLoadingTasks) {
                ErrorMessage(
                    errorCause = (uiState as GroupUiState.ErrorLoadingTasks).cause,
                    modifier = Modifier.padding(16.dp)
                )
            }

            TaskList(
                group = uiState.group,
                tasks = uiState.posts,
                onEditClick = { postId ->
                    viewModel.handleEvent(event = GroupUiEvent.EditPostClicked(postId = postId))
                }
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.handleEvent(event = GroupUiEvent.LoadGroup)
    }
}