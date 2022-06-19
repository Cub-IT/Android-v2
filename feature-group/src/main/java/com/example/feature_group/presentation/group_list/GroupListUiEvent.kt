package com.example.feature_group.presentation.group_list

import com.example.core.presentation.UiEvent

sealed class GroupListUiEvent : UiEvent() {

    object LoadGroups : GroupListUiEvent()

    object UserAvatarClicked : GroupListUiEvent()

    object AddGroupClicked : GroupListUiEvent()

    object JoinGroupClicked : GroupListUiEvent()

    data class OpenGroup(val groupId: String) : GroupListUiEvent()

}