package com.example.feature_group.presentation.group_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core.presentation.composable.ErrorMessage
import com.example.feature_group.presentation.common.composable.IconAvatar
import com.example.feature_group.presentation.group_list.composable.GroupList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupListScreen(
    viewModel: GroupListViewModel
) {
    val uiState by viewModel.uiState

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "My groups")
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.handleEvent(event = GroupListUiEvent.UserAvatarClicked)
                    }) {
                        IconAvatar(color = Color(0xFF3B79E8), size = 40.dp)
                    }
                    IconButton(onClick = {
                        viewModel.handleEvent(event = GroupListUiEvent.LoadGroups)
                    }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.handleEvent(event = GroupListUiEvent.AddButtonClicked)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        when (uiState) {
            is GroupListUiState.ErrorLoadingGroups -> {
                ErrorMessage(
                    errorDetails = (uiState as GroupListUiState.ErrorLoadingGroups).cause,
                    modifier = Modifier.padding(it).padding(16.dp)
                )
            }
            is GroupListUiState.GroupsFetched -> {
                GroupList(
                    groups = (uiState as GroupListUiState.GroupsFetched).groups,
                    modifier = Modifier.padding(it),
                    onGroupClick = { groupId ->
                        viewModel.handleEvent(event = GroupListUiEvent.OpenGroup(groupId))
                    }
                )
            }
            is GroupListUiState.LoadingGroups -> {
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

    LaunchedEffect(Unit) {
        viewModel.handleEvent(event = GroupListUiEvent.LoadGroups)
    }
}