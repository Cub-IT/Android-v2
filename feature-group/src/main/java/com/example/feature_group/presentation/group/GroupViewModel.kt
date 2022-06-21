package com.example.feature_group.presentation.group

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GroupViewModel @AssistedInject constructor(
    @Assisted("back") private val onBackClicked: () -> Unit,
    @Assisted("userAvatar") private val onUserAvatarClicked: () -> Unit,
    @Assisted var groupId: String,
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
                coverColor = Color(0xFF3B79E8)
            ),
            posts = emptyList()
        )
    }

    override fun handleEvent(event: GroupUiEvent) {
        when (event) {
            GroupUiEvent.LoadGroup -> updatePosts(currentState = uiState.value)
            GroupUiEvent.BackClicked -> {
                _uiState.value = createInitialState()
                onBackClicked()
            }
            GroupUiEvent.UserAvatarClicked -> onUserAvatarClicked()
        }.exhaustive
    }

    private fun updatePosts(currentState: GroupUiState) {
        val innerGroupId = groupId
        viewModelScope.launch {
            _uiState.value = GroupUiState.Loading(
                group = currentState.group,
                posts = currentState.posts
            )
            groupRepository.updateGroupPosts(groupId = innerGroupId).result(
                onSuccess = { /* state will be updated using flow */
                    val group = groupRepository.getUserGroupSuspend(groupId = innerGroupId)
                    val posts = groupRepository.getGroupPostsSuspend(groupId = innerGroupId).reversed()
                    _uiState.value = GroupUiState.TasksFetched(
                        group = group,
                        posts = posts.map {
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