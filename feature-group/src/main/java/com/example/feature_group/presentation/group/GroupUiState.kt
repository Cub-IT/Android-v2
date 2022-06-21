package com.example.feature_group.presentation.group

import com.example.core.presentation.UiState
import com.example.feature_group.presentation.common.item.GroupItem
import com.example.feature_group.presentation.group.item.PostItem

sealed class GroupUiState(
    val group: GroupItem,
    val posts: List<PostItem>,
    val isOwner: Boolean
    ) : UiState() {

    class Loading(group: GroupItem, posts: List<PostItem>, isOwner: Boolean)
        : GroupUiState(group = group, posts = posts, isOwner = isOwner)

    class TasksFetched(group: GroupItem, posts: List<PostItem>, isOwner: Boolean)
        : GroupUiState(group = group, posts = posts, isOwner = isOwner)

    class ErrorLoadingTasks(group: GroupItem, posts: List<PostItem>, isOwner: Boolean, val cause: String?)
        : GroupUiState(group = group, posts = posts, isOwner = isOwner)

}