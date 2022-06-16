package com.example.feature_group.presentation.group

import com.example.core.presentation.UiState
import com.example.feature_group.presentation.common.item.GroupItem
import com.example.feature_group.presentation.group.item.PostItem

sealed class GroupUiState(val group: GroupItem, val posts: List<PostItem>) : UiState() {

    class Loading(group: GroupItem, posts: List<PostItem>)
        : GroupUiState(group = group, posts = posts)

    class TasksFetched(group: GroupItem, posts: List<PostItem>)
        : GroupUiState(group = group, posts = posts)

    class ErrorLoadingTasks(group: GroupItem, posts: List<PostItem>, val cause: String?)
        : GroupUiState(group = group, posts = posts)

}