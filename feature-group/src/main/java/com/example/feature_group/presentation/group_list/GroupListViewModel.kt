package com.example.feature_group.presentation.group_list

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.BaseViewModel
import com.example.core.util.exhaustive
import com.example.core.util.result
import com.example.feature_group.data.repository.GroupRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GroupListViewModel @AssistedInject constructor(
    @Assisted private val onGroupClicked: (GroupId: String) -> Unit,
    @Assisted private val onUserAvatarClicked: () -> Unit,
    private val groupRepository: GroupRepository
) : BaseViewModel<GroupListUiEvent, GroupListUiState>() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted onGroupClicked: (groupId: String) -> Unit,
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
            is GroupListUiEvent.UserAvatarClicked -> onUserAvatarClicked()
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
            is GroupListUiEvent.UserAvatarClicked -> onUserAvatarClicked()
        }
    }

    private fun reduce(event: GroupListUiEvent, currentState: GroupListUiState.LoadingGroups) {
        when (event) {
            is GroupListUiEvent.AddButtonClicked -> TODO()
            is GroupListUiEvent.LoadGroups -> { }
            is GroupListUiEvent.OpenGroup -> throw IllegalStateException()
            is GroupListUiEvent.UserAvatarClicked -> onUserAvatarClicked()
        }
    }

    private fun loadGroups() {
        viewModelScope.launch {
            delay(10) // TODO: fix:  here I need to make a delay before using groupRepository (that I injected). If I don't then it crashes
            groupRepository.getUserGroups().result(
                onSuccess = { _uiState.value = GroupListUiState.GroupsFetched(it.value) },
                onFailure = {
                    _uiState.value = GroupListUiState.ErrorLoadingGroups(
                        cause = it.error.localizedMessage
                    )
                }
            )
        }
    }

}