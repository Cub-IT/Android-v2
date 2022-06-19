package com.example.feature_group.presentation.user

import com.example.core.presentation.UiState
import com.example.core.presentation.item.UserItem

sealed class UserUiState(val userItem: UserItem) : UiState() {

    class UpdatingUserItem(userItem: UserItem) : UserUiState(userItem = userItem)

    class UserItemFetched(userItem: UserItem) : UserUiState(userItem = userItem)

}