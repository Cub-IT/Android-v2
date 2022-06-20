package com.example.feature_group.presentation.group_list

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.BaseViewModel
import com.example.core.util.exhaustive
import com.example.core.util.result
import com.example.feature_group.data.repository.GroupRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class GroupListViewModel @AssistedInject constructor(
    @Assisted private val onGroupClicked: (GroupId: String) -> Unit,
    @Assisted("userAvatar") private val onUserAvatarClicked: () -> Unit,
    @Assisted("addGroup") private val onAddGroupClicked: () -> Unit,
    @Assisted("joinGroup") private val onJoinGroupClicked: () -> Unit,
    private val groupRepository: GroupRepository
) : BaseViewModel<GroupListUiEvent, GroupListUiState>() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted onGroupClicked: (groupId: String) -> Unit,
            @Assisted("userAvatar") onUserAvatarClicked: () -> Unit,
            @Assisted("addGroup") onAddGroupClicked: () -> Unit,
            @Assisted("joinGroup") onJoinGroupClicked: () -> Unit
        ): GroupListViewModel
    }

    override fun createInitialState(): GroupListUiState {
        return GroupListUiState.LoadingGroups(emptyList())
    }

    init {
        groupRepository.getUserGroups().onEach {
            _uiState.value = GroupListUiState.GroupsFetched(it)
        }.launchIn(viewModelScope)
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
            is GroupListUiEvent.AddGroupClicked -> onAddGroupClicked()
            is GroupListUiEvent.JoinGroupClicked -> onJoinGroupClicked()
            is GroupListUiEvent.LoadGroups -> updateGroups()
            is GroupListUiEvent.OpenGroup -> throw IllegalStateException()
            is GroupListUiEvent.UserAvatarClicked -> onUserAvatarClicked()
        }
    }

    private fun reduce(event: GroupListUiEvent, currentState: GroupListUiState.GroupsFetched) {
        when (event) {
            is GroupListUiEvent.AddGroupClicked -> onAddGroupClicked()
            is GroupListUiEvent.JoinGroupClicked -> onJoinGroupClicked()
            is GroupListUiEvent.LoadGroups -> updateGroups()
            is GroupListUiEvent.OpenGroup -> onGroupClicked(event.groupId)
            is GroupListUiEvent.UserAvatarClicked -> onUserAvatarClicked()
        }
    }

    private fun reduce(event: GroupListUiEvent, currentState: GroupListUiState.LoadingGroups) {
        when (event) {
            is GroupListUiEvent.AddGroupClicked -> onAddGroupClicked()
            is GroupListUiEvent.JoinGroupClicked -> onJoinGroupClicked()
            is GroupListUiEvent.LoadGroups -> updateGroups()
            is GroupListUiEvent.OpenGroup -> throw IllegalStateException()
            is GroupListUiEvent.UserAvatarClicked -> onUserAvatarClicked()
        }
    }

    private fun updateGroups() {
        viewModelScope.launch {
            _uiState.value = GroupListUiState.LoadingGroups(groups = uiState.value.groups)
            groupRepository.updateUserGroups().result(
                onSuccess = { /* state will be updated using flow */ },
                onFailure = {
                    _uiState.value = GroupListUiState.ErrorLoadingGroups(
                        groups = uiState.value.groups,
                        cause = it.error.localizedMessage
                    )
                }
            )
        }
    }

}