package com.example.feature_group.presentation.user

import com.example.core.presentation.UiEvent

sealed class UserUiEvent : UiEvent() {

    object UpdateUserData : UserUiEvent()

    object BackClicked : UserUiEvent()

    object LogoutClicked : UserUiEvent()

}