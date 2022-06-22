package com.example.feature_group.presentation.edit_post

import com.example.core.presentation.UiState
import com.example.core.presentation.item.InputFiled

sealed class EditPostUiState(
    val postContent: InputFiled,
    val isEditingEnabled: Boolean
) : UiState() {

    class WaitingPostData(postContent: InputFiled, isPostingEnabled: Boolean)
        : EditPostUiState(postContent = postContent, isEditingEnabled = isPostingEnabled)

    class WaitingResponse(postContent: InputFiled)
        : EditPostUiState(postContent = postContent, isEditingEnabled = false)

    class FailedAddingPost(postContent: InputFiled, isPostingEnabled: Boolean, val cause: String?)
        : EditPostUiState(postContent = postContent, isEditingEnabled = isPostingEnabled)
}
