package com.example.feature_group.presentation.join_group

import com.example.core.presentation.UiEvent

sealed class JoinGroupUiEvent : UiEvent() {
    class UpdateGroupCode(val code: String) : JoinGroupUiEvent()
    object JoinGroup : JoinGroupUiEvent()
    object BackClicked : JoinGroupUiEvent()
}