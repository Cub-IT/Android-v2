package com.example.feature_group.presentation.group

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.feature_group.presentation.common.composable.IconAvatar
import com.example.feature_group.presentation.group.composable.TaskList
import com.example.feature_group.presentation.group_list.GroupListUiState

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
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        IconAvatar(color = Color(0xFF3B79E8), size = 40.dp)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
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
        TODO("Not yet implemented")
        it



        when (uiState) {
            is GroupListUiState.ErrorLoadingGroups -> TODO()
            is GroupListUiState.GroupsFetched -> {
                //TaskList(group =  , tasks = )
            }
            is GroupListUiState.LoadingGroups -> TODO()
        }
    }
}