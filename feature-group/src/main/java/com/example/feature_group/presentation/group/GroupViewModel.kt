package com.example.feature_group.presentation.group

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core.data.local.UserSource
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
    @Assisted("addPost") private val onAddPostClicked: () -> Unit,
    @Assisted("editPost") private val onEditPostClicked: (postId: String) -> Unit,
    @Assisted var groupId: String,
    private val groupRepository: GroupRepository,
    private val userSource: UserSource
) : BaseViewModel<GroupUiEvent, GroupUiState>() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("back") onBackClicked: () -> Unit,
            @Assisted("userAvatar") onUserAvatarClicked: () -> Unit,
            @Assisted("addPost") onAddPostClicked: () -> Unit,
            @Assisted("editPost") onEditPostClicked: (postId: String) -> Unit,
            @Assisted groupId: String
        ): GroupViewModel
    }

    override fun createInitialState(): GroupUiState {
        return GroupUiState.Loading(
            group = GroupItem(
                id = groupId,
                ownerId = "0",
                name = "",
                description = "",
                code = "",
                ownerName = "",
                coverColor = Color(0xFF3B79E8)
            ),
            posts = emptyList(),
            isOwner = false
        )
    }

    override fun handleEvent(event: GroupUiEvent) {
        when (event) {
            is GroupUiEvent.LoadGroup -> updatePosts(currentState = uiState.value)
            is GroupUiEvent.BackClicked -> {
                _uiState.value = createInitialState()
                onBackClicked()
            }
            is GroupUiEvent.UserAvatarClicked -> onUserAvatarClicked()
            is GroupUiEvent.AddPostClicked -> onAddPostClicked()
            is GroupUiEvent.EditPostClicked -> onEditPostClicked(event.postId)
        }.exhaustive
    }

    private fun updatePosts(currentState: GroupUiState) {
        val innerGroupId = groupId
        viewModelScope.launch {
            _uiState.value = GroupUiState.Loading(
                group = currentState.group,
                posts = currentState.posts,
                isOwner = userSource.getUser()!!.id == currentState.group.ownerId
            )
            groupRepository.updateGroupPosts(groupId = innerGroupId).result(
                onSuccess = { /* state will be updated using flow */
                    val group = groupRepository.getUserGroupSuspend(groupId = innerGroupId)
                    val posts = groupRepository.getGroupPostsSuspend(groupId = innerGroupId).reversed()
                    _uiState.value = GroupUiState.TasksFetched(
                        group = group,
                        posts = posts.map {
                            PostItem(
                                id = it.id,
                                creatorName = group.ownerName,
                                creatorColor = Color(0xFF3B79E8),
                                creationDate = it.creationDate,
                                content = it.description,
                                isModified = userSource.getUser()!!.id == group.ownerId
                            )
                        },
                        isOwner = userSource.getUser()!!.id == group.ownerId
                    )
                },
                onFailure = {
                    _uiState.value = GroupUiState.ErrorLoadingTasks(
                        group = currentState.group,
                        posts = currentState.posts,
                        isOwner = currentState.isOwner,
                        cause = it.error.readableCause()
                    )
                }
            )
        }
    }

}