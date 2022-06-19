package com.example.feature_group.presentation.join_group

import com.example.core.presentation.UiState
import com.example.core.presentation.item.InputFiled

sealed class JoinGroupUiState(
    val groupCode: InputFiled,
    val isJoiningEnabled: Boolean
) : UiState() {

    class WaitingGroupData(groupCode: InputFiled, isJoiningEnabled: Boolean)
        : JoinGroupUiState(groupCode = groupCode, isJoiningEnabled = isJoiningEnabled)

    class WaitingResponse(groupCode: InputFiled)
        : JoinGroupUiState(groupCode = groupCode, isJoiningEnabled = false)

    class FailedCreation(groupCode: InputFiled, isJoiningEnabled: Boolean, val cause: String?)
        : JoinGroupUiState(groupCode = groupCode, isJoiningEnabled = isJoiningEnabled)
}