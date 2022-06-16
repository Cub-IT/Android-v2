package com.example.feature_group.presentation.group_list

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.example.core.presentation.BaseViewModel
import com.example.core.util.exhaustive
import com.example.feature_group.presentation.common.item.GroupItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GroupListViewModel @AssistedInject constructor(
    @Assisted private val onGroupClicked: (GroupId: String) -> Unit,
    @Assisted private val onUserAvatarClicked: () -> Unit
) : BaseViewModel<GroupListUiEvent, GroupListUiState>() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted onGroupClicked: (GroupId: String) -> Unit,
            @Assisted onUserAvatarClicked: () -> Unit
        ): GroupListViewModel
    }

    override fun createInitialState(): GroupListUiState {
        loadGroups()
        return GroupListUiState.LoadingGroups(emptyList())
    }

    override fun handleEvent(event: GroupListUiEvent) {
        when (val currentState = _uiState.value) {
            is GroupListUiState.ErrorLoadingGroups -> reduce(event, currentState)
            is GroupListUiState.GroupsFetched ->  reduce(event, currentState)
            is GroupListUiState.LoadingGroups ->  reduce(event, currentState)
        }.exhaustive
    }

    private fun reduce(event: GroupListUiEvent, currentState: GroupListUiState.ErrorLoadingGroups) {
        when (event) {
            is GroupListUiEvent.AddButtonClicked -> TODO()
            is GroupListUiEvent.LoadGroups -> {
                _uiState.value = GroupListUiState.LoadingGroups(groups = emptyList())
                loadGroups()
            }
            is GroupListUiEvent.OpenGroup -> throw IllegalStateException()
            is GroupListUiEvent.UserAvatarClicked -> onUserAvatarClicked
        }
    }

    private fun reduce(event: GroupListUiEvent, currentState: GroupListUiState.GroupsFetched) {
        when (event) {
            is GroupListUiEvent.AddButtonClicked -> TODO()
            is GroupListUiEvent.LoadGroups -> {
                _uiState.value = GroupListUiState.LoadingGroups(groups = currentState.groups)
                loadGroups()
            }
            is GroupListUiEvent.OpenGroup -> onGroupClicked(event.groupId)
            is GroupListUiEvent.UserAvatarClicked -> onUserAvatarClicked
        }
    }

    private fun reduce(event: GroupListUiEvent, currentState: GroupListUiState.LoadingGroups) {
        when (event) {
            is GroupListUiEvent.AddButtonClicked -> TODO()
            is GroupListUiEvent.LoadGroups -> { }
            is GroupListUiEvent.OpenGroup -> throw IllegalStateException()
            is GroupListUiEvent.UserAvatarClicked -> onUserAvatarClicked
        }
    }

    private fun loadGroups() {
        viewModelScope.launch {
            delay(2000)
            /*_uiState.value = GroupListUiState.GroupsFetched(
                groups = listOf(
                    GroupItem(
                        name = "Group name 1",
                        description = "Here is a description",
                        ownerName = "Teacher Name 1",
                        coverColor = Color(0xFF0277BD)
                    ),
                    GroupItem(
                        name = "Group name 2",
                        description = "Here is a description",
                        ownerName = "Teacher Name 2",
                        coverColor = Color(0xFF3B79E8)
                    ),
                    GroupItem(
                        name = "Group name 3",
                        description = "Here is a description",
                        ownerName = "Teacher Name 3",
                        coverColor = Color(0xFF32AC71)
                    ),
                    GroupItem(
                        name = "Group name 4",
                        description = "Here is a description",
                        ownerName = "Teacher Name 4",
                        coverColor = Color(0xFF566E7A)
                    ),
                    GroupItem(
                        name = "Group name 5",
                        description = "Here is a description",
                        ownerName = "Teacher Name 5",
                        coverColor = Color(0xFFD91A60)
                    ),
                    GroupItem(
                        name = "Group name 6",
                        description = "Here is a description",
                        ownerName = "Teacher Name 6",
                        coverColor = Color(0xFF02579A)
                    )
                )
            )*/
            _uiState.value = GroupListUiState.ErrorLoadingGroups(cause = IllegalArgumentException().localizedMessage)
        }
    }

}