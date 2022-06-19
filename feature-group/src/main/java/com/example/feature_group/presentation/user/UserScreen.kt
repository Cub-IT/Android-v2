package com.example.feature_group.presentation.user

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.feature_group.presentation.common.composable.IconAvatar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    viewModel: UserViewModel
) {
    val uiState by viewModel.uiState

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Profile")
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.handleEvent(event = UserUiEvent.BackClicked) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (uiState is UserUiState.UpdatingUserItem) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconAvatar(color = Color(0xFF3B79E8), size = 96.dp)

                Spacer(modifier = Modifier.padding(16.dp))
                Text(text = "${uiState.userItem.firstName} ${uiState.userItem.lastName}")
                Divider(modifier = Modifier.width(32.dp).padding(vertical = 12.dp))
                Text(text = uiState.userItem.email)
                Spacer(modifier = Modifier.padding(64.dp))

                OutlinedButton(onClick = { viewModel.handleEvent(event = UserUiEvent.LogoutClicked) }) {
                    Text(text = "Logout")
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.handleEvent(event = UserUiEvent.UpdateUserData)
    }
}