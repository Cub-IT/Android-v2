package com.example.feature_group.presentation.group

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.example.core.presentation.BaseViewModel
import com.example.core.util.readableCause
import com.example.core.util.exhaustive
import com.example.core.util.result
import com.example.feature_group.data.repository.GroupRepository
import com.example.feature_group.presentation.common.item.GroupItem
import com.example.feature_group.presentation.group.item.PostItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class GroupViewModel @AssistedInject constructor(
    @Assisted("back") private val onBackClicked: () -> Unit,
    @Assisted("userAvatar") private val onUserAvatarClicked: () -> Unit,
    @Assisted private val groupId: String,
    private val groupRepository: GroupRepository
) : BaseViewModel<GroupUiEvent, GroupUiState>() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("back") onBackClicked: () -> Unit,
            @Assisted("userAvatar") onUserAvatarClicked: () -> Unit,
            @Assisted groupId: String
        ): GroupViewModel
    }

    override fun createInitialState(): GroupUiState {
        return GroupUiState.Loading(
            group = GroupItem(
                id = groupId,
                name = "",
                description = "",
                ownerName = "",
                coverColor = Color.Magenta
            ),
            posts = emptyList()
        )
    }

    init {
        viewModelScope.launch {
            groupRepository.updateGroupPosts(groupId = groupId)
            val group = groupRepository.getUserGroupSuspend(groupId = groupId)
            _uiState.value = GroupUiState.TasksFetched(
                group = group,
                posts = groupRepository.getGroupPostsSuspend(groupId = groupId).map {
                    PostItem(
                        creatorName = group.ownerName,
                        creatorColor = Color(0xFF3B79E8),
                        creationDate = it.creationDate,
                        content = it.description
                    )
                }
            )
        }
        /*combine(
            groupRepository.getUserGroup(groupId = groupId),
            groupRepository.getGroupPosts(groupId = groupId)
        ) { group, posts ->
            Pair(group, posts)
        }.onEach {
            _uiState.value = GroupUiState.TasksFetched(
                group = it.first,
                posts = it.second.map { postEntity ->
                    PostItem(
                        creatorName = it.first.ownerName,
                        creatorColor = Color.Magenta,
                        creationDate = postEntity.creationDate,
                        content = postEntity.description
                    )
                }
            )
        }.launchIn(viewModelScope)*/
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
            GroupUiEvent.LoadGroup -> updatePosts(currentState = currentState)
            GroupUiEvent.BackClicked -> onBackClicked()
            GroupUiEvent.UserAvatarClicked -> onUserAvatarClicked()
        }.exhaustive
    }

    private fun reduce(event: GroupUiEvent, currentState: GroupUiState.Loading) {
        when (event) {
            GroupUiEvent.LoadGroup -> updatePosts(currentState = currentState)
            GroupUiEvent.BackClicked -> onBackClicked()
            GroupUiEvent.UserAvatarClicked -> onUserAvatarClicked()
        }.exhaustive
    }

    private fun reduce(event: GroupUiEvent, currentState: GroupUiState.TasksFetched) {
        when (event) {
            GroupUiEvent.LoadGroup -> updatePosts(currentState = currentState)
            GroupUiEvent.BackClicked -> onBackClicked()
            GroupUiEvent.UserAvatarClicked -> onUserAvatarClicked()
        }.exhaustive
    }

    private fun updatePosts(currentState: GroupUiState) {
        viewModelScope.launch {
            _uiState.value = GroupUiState.Loading(
                group = currentState.group,
                posts = currentState.posts
            )
            groupRepository.updateGroupPosts(groupId = currentState.group.id).result(
                onSuccess = { /* state will be updated using flow */
                    val group = groupRepository.getUserGroupSuspend(groupId = groupId)
                    _uiState.value = GroupUiState.TasksFetched(
                        group = group,
                        posts = groupRepository.getGroupPostsSuspend(groupId = groupId).map {
                            PostItem(
                                creatorName = group.ownerName,
                                creatorColor = Color(0xFF3B79E8),
                                creationDate = it.creationDate,
                                content = it.description
                            )
                        }
                    )
                },
                onFailure = {
                    _uiState.value = GroupUiState.ErrorLoadingTasks(
                        group = currentState.group,
                        posts = currentState.posts,
                        cause = it.error.readableCause()
                    )
                }
            )
        }
    }

}