package com.example.feature_group.presentation.add_group

import com.example.core.presentation.UiEvent

sealed class AddGroupUiEvent : UiEvent() {
    class UpdateGroupName(val name: String) : AddGroupUiEvent()
    class UpdateGroupDescription(val description: String) : AddGroupUiEvent()
    object CreateGroup : AddGroupUiEvent()
    object BackClicked : AddGroupUiEvent()
}