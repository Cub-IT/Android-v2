package com.example.feature_group.presentation.add_group

import com.example.core.presentation.UiState
import com.example.feature_group.presentation.add_group.item.NewGroupItem

sealed class AddGroupUiState(
    val group: NewGroupItem,
    val isCreationEnabled: Boolean
) : UiState() {

    class WaitingGroupData(group: NewGroupItem, isCreationEnabled: Boolean)
        : AddGroupUiState(group = group, isCreationEnabled = isCreationEnabled)

    class WaitingResponse(group: NewGroupItem)
        : AddGroupUiState(group = group, isCreationEnabled = false)

    class FailedCreation(group: NewGroupItem, isCreationEnabled: Boolean, val cause: String?)
        : AddGroupUiState(group = group, isCreationEnabled = isCreationEnabled)
}