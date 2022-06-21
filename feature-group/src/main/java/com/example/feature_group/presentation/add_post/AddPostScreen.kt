package com.example.feature_group.presentation.add_post

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostScreen(
    viewModel: AddPostViewModel
) {
    val uiState by viewModel.uiState

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Add post")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.handleEvent(event = AddPostUiEvent.BackClicked)
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    FilledTonalButton(
                        onClick = { viewModel.handleEvent(event = AddPostUiEvent.AddPost) },
                        enabled = uiState.isPostingEnabled
                    ) {
                        Text(text = "Create")
                    }
                }
            )
        }
    ) {
        when (uiState) {
            is AddPostUiState.FailedAddingPost,
            is AddPostUiState.WaitingPostData -> {
                Box(Modifier.fillMaxSize()) {
                    if (uiState is AddPostUiState.FailedAddingPost) {
                        ErrorMessage(
                            errorCause = (uiState as AddPostUiState.FailedAddingPost).cause,
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
                        Text(text = "Type the message you want to send here.")
                        UndefinedOutlinedTextField(
                            field = uiState.postContent,
                            label = R.string.post_content,
                            onValueChange = { newValue ->
                                viewModel.handleEvent(event = AddPostUiEvent.UpdatePostContent(newValue))
                            }
                        )
                    }
                }
            }
            is AddPostUiState.WaitingResponse -> {
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