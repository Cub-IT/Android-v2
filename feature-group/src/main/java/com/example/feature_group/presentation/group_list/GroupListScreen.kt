package com.example.feature_group.presentation.group_list

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core.presentation.composable.ErrorMessage
import com.example.feature_group.presentation.common.composable.IconAvatar
import com.example.feature_group.presentation.group_list.composable.GroupList
import com.example.feature_group.R

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
            Column {
                FloatingActionButton(onClick = {
                    viewModel.handleEvent(event = GroupListUiEvent.JoinGroupClicked)
                }) {
                    Icon(painter = painterResource(R.drawable.ic_baseline_group_add_24), contentDescription = null)
                }

                Spacer(modifier = Modifier.padding(8.dp))

                FloatingActionButton(onClick = {
                    viewModel.handleEvent(event = GroupListUiEvent.AddGroupClicked)
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        }
    ) {
        Column(modifier = Modifier.padding(it).fillMaxSize()) {
            if (uiState is GroupListUiState.LoadingGroups) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            if (uiState is GroupListUiState.ErrorLoadingGroups) {
                ErrorMessage(
                    errorCause = (uiState as GroupListUiState.ErrorLoadingGroups).cause,
                    modifier = Modifier.padding(16.dp)
                )
            }

            GroupList(
                groups = uiState.groups,
                onGroupClick = { groupId ->
                    viewModel.handleEvent(event = GroupListUiEvent.OpenGroup(groupId))
                }
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.handleEvent(event = GroupListUiEvent.LoadGroups)
    }
}