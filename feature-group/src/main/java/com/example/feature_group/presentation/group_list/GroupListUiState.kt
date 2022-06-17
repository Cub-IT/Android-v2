package com.example.feature_group.presentation.group_list

import com.example.core.presentation.UiState
import com.example.feature_group.presentation.common.item.GroupItem

sealed class GroupListUiState : UiState() {

    data class LoadingGroups(val groups: List<GroupItem>) : GroupListUiState()

    data class GroupsFetched(val groups: List<GroupItem>) : GroupListUiState()

    data class ErrorLoadingGroups(val cause: String?) : GroupListUiState()

}