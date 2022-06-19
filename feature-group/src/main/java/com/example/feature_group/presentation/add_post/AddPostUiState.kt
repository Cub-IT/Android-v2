package com.example.feature_group.presentation.add_post

import com.example.core.presentation.UiState
import com.example.core.presentation.item.InputFiled

sealed class AddPostUiState(
    val postContent: InputFiled,
    val isPostingEnabled: Boolean
) : UiState() {

    class WaitingPostData(postContent: InputFiled, isPostingEnabled: Boolean)
        : AddPostUiState(postContent = postContent, isPostingEnabled = isPostingEnabled)

    class WaitingResponse(postContent: InputFiled)
        : AddPostUiState(postContent = postContent, isPostingEnabled = false)

    class FailedAddingPost(postContent: InputFiled, isPostingEnabled: Boolean, val cause: String?)
        : AddPostUiState(postContent = postContent, isPostingEnabled = isPostingEnabled)
}