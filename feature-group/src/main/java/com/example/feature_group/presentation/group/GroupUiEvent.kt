package com.example.feature_group.presentation.group

import com.example.core.presentation.UiEvent

sealed class GroupUiEvent : UiEvent() {

    object LoadGroup : GroupUiEvent()

}