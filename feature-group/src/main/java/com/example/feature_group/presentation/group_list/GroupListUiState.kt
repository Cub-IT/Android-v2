package com.example.feature_group.presentation.group_list

import com.example.core.presentation.UiState
import com.example.feature_group.presentation.common.item.GroupItem

sealed class GroupListUiState(val groups: List<GroupItem>) : UiState() {

    class LoadingGroups(groups: List<GroupItem>) : GroupListUiState(groups = groups)

    class GroupsFetched(groups: List<GroupItem>) : GroupListUiState(groups = groups)

    class ErrorLoadingGroups(groups: List<GroupItem>, val cause: String?) : GroupListUiState(groups = groups)

}