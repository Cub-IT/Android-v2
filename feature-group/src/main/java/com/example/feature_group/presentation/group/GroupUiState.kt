package com.example.feature_group.presentation.group

import com.example.core.presentation.UiState
import com.example.feature_group.presentation.group.item.PostItem

sealed class GroupUiState : UiState() {

    object Loading : GroupUiState()

    data class TasksFetched(val posts: List<PostItem>) : GroupUiState()

    class ErrorLoadingTasks(val posts: List<PostItem>, reason: String) : GroupUiState()

}