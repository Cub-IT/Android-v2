package com.example.feature_group.presentation.edit_post

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.BaseViewModel
import com.example.core.presentation.item.InputFiled
import com.example.core.util.InputLinter
import com.example.core.util.exhaustive
import com.example.core.util.readableCause
import com.example.core.util.result
import com.example.feature_group.data.repository.GroupRepository
import com.example.feature_group.presentation.add_post.AddPostUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import javax.inject.Named

class EditPostViewModel @AssistedInject constructor(
    @Assisted("editPost") private val onEditClicked: () -> Unit,
    @Assisted("back") private val onBackClicked: () -> Unit,
    @Assisted var postId: String,
    private val groupRepository: GroupRepository,
    @Named("postContentLinter") private val postContentLinter: InputLinter
) : BaseViewModel<EditPostUiEvent, AddPostUiState>() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("editPost") onEditClicked: () -> Unit,
            @Assisted("back") onBackClicked: () -> Unit,
            @Assisted postId: String,
        ): EditPostViewModel
    }

    override fun createInitialState(): AddPostUiState {
        val postContent = InputFiled("")
        return AddPostUiState.WaitingPostData(
            postContent = postContent,
            isPostingEnabled = isPostingEnabled(postContent)
        )
    }

    override fun handleEvent(event: EditPostUiEvent) {
        when (val currentState = uiState.value) {
            is AddPostUiState.FailedAddingPost -> reduce(event, currentState)
            is AddPostUiState.WaitingPostData -> reduce(event, currentState)
            is AddPostUiState.WaitingResponse -> throw IllegalStateException()
        }.exhaustive
    }

    private fun reduce(event: EditPostUiEvent, currentState: AddPostUiState.FailedAddingPost) {
        when (event) {
            is EditPostUiEvent.BackClicked -> onBackClicked()
            is EditPostUiEvent.AddPost -> updatePost(currentState.postContent)
            is EditPostUiEvent.UpdatePostContent -> {
                val postContent = getNewContent(event.content)
                _uiState.value = AddPostUiState.FailedAddingPost(
                    postContent = postContent,
                    isPostingEnabled = isPostingEnabled(postContent),
                    cause = currentState.cause
                )
            }
            EditPostUiEvent.LoadPost -> getPost()
        }.exhaustive
    }

    private fun reduce(event: EditPostUiEvent, currentState: AddPostUiState.WaitingPostData) {
        when (event) {
            is EditPostUiEvent.BackClicked -> onBackClicked()
            is EditPostUiEvent.AddPost -> updatePost(currentState.postContent)
            is EditPostUiEvent.UpdatePostContent -> {
                val postContent = getNewContent(event.content)
                _uiState.value = AddPostUiState.WaitingPostData(
                    postContent = postContent,
                    isPostingEnabled = isPostingEnabled(postContent)
                )
            }
            EditPostUiEvent.LoadPost -> getPost()
        }.exhaustive
    }

    private fun isPostingEnabled(postContent: InputFiled): Boolean {
        return (postContent.error == null) and
                postContent.value.isNotEmpty()
    }

    private fun getPost() {
        viewModelScope.launch {
            val post = groupRepository.getPost(postId = postId)
            val inputFiled = getNewContent(post.description)
            _uiState.value = AddPostUiState.WaitingPostData(
                postContent = inputFiled,
                isPostingEnabled(inputFiled)
            )
        }
    }

    private fun updatePost(postContent: InputFiled) {
        viewModelScope.launch {
            groupRepository.updatePost(postId = postId, content = postContent.value).result(
                onSuccess = { onEditClicked() },
                onFailure = {
                    _uiState.value = AddPostUiState.FailedAddingPost(
                        postContent = postContent,
                        isPostingEnabled = isPostingEnabled(postContent),
                        cause = it.error.readableCause()
                    )
                }
            )
        }
    }

    private fun getNewContent(code: String): InputFiled {
        return InputFiled(
            value = code,
            error = postContentLinter.check(code.trim())
        )
    }

}
