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
                    IconButton(onClick = { /*TODO*/ }) {
                        IconAvatar(color = Color(0xFF3B79E8), size = 40.dp)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        when (uiState) {
            is GroupListUiState.ErrorLoadingGroups -> TODO()
            is GroupListUiState.GroupsFetched -> {
                GroupList(
                    groups = (uiState as GroupListUiState.GroupsFetched).groups,
                    modifier = Modifier.padding(it)
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