package com.example.feature_group.presentation.add_post

import com.example.core.presentation.UiEvent

sealed class AddPostUiEvent : UiEvent() {
    class UpdatePostContent(val content: String) : AddPostUiEvent()
    object AddPost : AddPostUiEvent()
    object BackClicked : AddPostUiEvent()
}