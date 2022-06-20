package com.example.feature_group.presentation.user

import androidx.lifecycle.viewModelScope
import com.example.core.data.local.UserSource
import com.example.core.presentation.BaseViewModel
import com.example.core.presentation.item.UserItem
import com.example.core.util.exhaustive
import com.example.feature_group.data.repository.GroupRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class UserViewModel @AssistedInject constructor(
    @Assisted("back") private val onBackClicked: () -> Unit,
    @Assisted("logout") private val onLogoutClicked: () -> Unit,
    private val userSource: UserSource,
    private val groupRepository: GroupRepository
) : BaseViewModel<UserUiEvent, UserUiState>() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("back") onBackClicked: () -> Unit,
            @Assisted("logout") onLogoutClicked: () -> Unit
        ): UserViewModel
    }

    override fun createInitialState(): UserUiState {
        viewModelScope.launch {
            updateUserData()
        }

        return UserUiState.UpdatingUserItem(
            userItem = UserItem(
                id = "",
                firstName = "First name",
                lastName = "Last name",
                email = "Email"
            )
        )
    }

    override fun handleEvent(event: UserUiEvent) {
        when (val currentState = _uiState.value) {
            is UserUiState.UpdatingUserItem -> reduce(event, currentState)
            is UserUiState.UserItemFetched -> reduce(event, currentState)
        }.exhaustive
    }

    private fun reduce(event: UserUiEvent, currentState: UserUiState.UpdatingUserItem) {
        when (event) {
            UserUiEvent.BackClicked -> onBackClicked()
            UserUiEvent.UpdateUserData -> { /* nothing to do */ }
            UserUiEvent.LogoutClicked -> {
                GlobalScope.launch { groupRepository.logout() }
                onLogoutClicked()
            }
        }.exhaustive
    }

    private fun reduce(event: UserUiEvent, currentState: UserUiState.UserItemFetched) {
        when (event) {
            UserUiEvent.BackClicked -> onBackClicked()
            UserUiEvent.UpdateUserData -> updateUserData()
            UserUiEvent.LogoutClicked -> {
                GlobalScope.launch { groupRepository.logout() }
                onLogoutClicked()
            }
        }.exhaustive
    }

    private fun updateUserData() {
        viewModelScope.launch {
            delay(10)
            _uiState.value = UserUiState.UserItemFetched(
                userItem = userSource.getUser()
                    ?: throw IllegalStateException("User is authorized but user's id is ${userSource.getUser()?.id}")
            )
            _uiState.value = UserUiState.UpdatingUserItem(userItem = uiState.value.userItem)
            // TODO: getting new user data
            delay(2000)
            _uiState.value = UserUiState.UserItemFetched(
                userItem = _uiState.value.userItem
            )
        }
    }

}