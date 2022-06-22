package com.example.feature_group.presentation.edit_post

import com.example.core.presentation.UiEvent

sealed class EditPostUiEvent : UiEvent() {
    class UpdatePostContent(val content: String) : EditPostUiEvent()
    object AddPost : EditPostUiEvent()
    object BackClicked : EditPostUiEvent()
    object LoadPost : EditPostUiEvent()
}
