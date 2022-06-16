package com.example.feature_group.presentation.group

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.example.core.presentation.BaseViewModel
import com.example.core.util.exhaustive
import com.example.feature_group.presentation.common.item.GroupItem
import com.example.feature_group.presentation.group_list.GroupListUiEvent
import com.example.feature_group.presentation.group_list.GroupListUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class GroupViewModel @AssistedInject constructor(
    @Assisted("back") private val onBackClicked: () -> Unit,
    @Assisted("userAvatar") private val onUserAvatarClicked: () -> Unit,

) : BaseViewModel<GroupUiEvent, GroupUiState>() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("back") onBackClicked: () -> Unit,
            @Assisted("userAvatar") onUserAvatarClicked: () -> Unit
        ): GroupViewModel
    }

    override fun createInitialState(): GroupUiState {
        return GroupUiState.Loading(
            group = GroupItem(
                name = "",
                description = "",
                ownerName = "",
                coverColor = Color.Magenta
            ),
            posts = emptyList()
        )
    }

    override fun handleEvent(event: GroupUiEvent) {
        when (val currentState = _uiState.value) {
            is GroupUiState.ErrorLoadingTasks -> reduce(event, currentState)
            is GroupUiState.Loading -> reduce(event, currentState)
            is GroupUiState.TasksFetched -> reduce(event, currentState)
        }.exhaustive
    }

    private fun reduce(event: GroupUiEvent, currentState: GroupUiState.ErrorLoadingTasks) {
        when (event) {
            GroupUiEvent.LoadGroup -> TODO()
            GroupUiEvent.BackClicked -> TODO()
            GroupUiEvent.UserAvatarClicked -> TODO()
        }.exhaustive
    }

    private fun reduce(event: GroupUiEvent, currentState: GroupUiState.Loading) {
        when (event) {
            GroupUiEvent.LoadGroup -> TODO()
            GroupUiEvent.BackClicked -> TODO()
            GroupUiEvent.UserAvatarClicked -> TODO()
        }.exhaustive
    }

    private fun reduce(event: GroupUiEvent, currentState: GroupUiState.TasksFetched) {
        when (event) {
            GroupUiEvent.LoadGroup -> TODO()
            GroupUiEvent.BackClicked -> TODO()
            GroupUiEvent.UserAvatarClicked -> TODO()
        }.exhaustive
    }

    private fun loadGroups(currentState: GroupUiState) {
        _uiState.value = GroupUiState.Loading(
            group = currentState.group,
            posts = currentState.posts
        )
        viewModelScope.launch {
            // TODO: getting groups
        }
    }

}