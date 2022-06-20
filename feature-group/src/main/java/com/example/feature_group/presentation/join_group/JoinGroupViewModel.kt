package com.example.feature_group.presentation.join_group

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.BaseViewModel
import com.example.core.presentation.item.InputFiled
import com.example.core.util.InputLinter
import com.example.core.util.readableCause
import com.example.core.util.exhaustive
import com.example.core.util.result
import com.example.feature_group.data.repository.GroupRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import javax.inject.Named

class JoinGroupViewModel @AssistedInject constructor(
    @Assisted("join") private val onJoinClicked: () -> Unit,
    @Assisted("back") private val onBackClicked: () -> Unit,
    private val groupRepository: GroupRepository,
    @Named("groupCodeLinter") private val groupCodeLinter: InputLinter
) : BaseViewModel<JoinGroupUiEvent, JoinGroupUiState>() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("join") onCreateClicked: () -> Unit,
            @Assisted("back") onBackClicked: () -> Unit
        ): JoinGroupViewModel
    }

    override fun createInitialState(): JoinGroupUiState {
        val groupCode = InputFiled("")
        return JoinGroupUiState.WaitingGroupData(
            groupCode = groupCode,
            isJoiningEnabled = isJoiningEnabled(groupCode)
        )
    }

    override fun handleEvent(event: JoinGroupUiEvent) {
        when (val currentState = uiState.value) {
            is JoinGroupUiState.FailedCreation -> reduce(event, currentState)
            is JoinGroupUiState.WaitingGroupData -> reduce(event, currentState)
            is JoinGroupUiState.WaitingResponse -> throw IllegalStateException()
        }.exhaustive
    }

    private fun reduce(event: JoinGroupUiEvent, currentState: JoinGroupUiState.FailedCreation) {
        when (event) {
            is JoinGroupUiEvent.BackClicked -> onBackClicked()
            is JoinGroupUiEvent.JoinGroup -> createNewGroup(currentState.groupCode)
            is JoinGroupUiEvent.UpdateGroupCode -> {
                val groupCode = getNewCode(event.code)
                _uiState.value = JoinGroupUiState.FailedCreation(
                    groupCode = groupCode,
                    isJoiningEnabled = isJoiningEnabled(groupCode),
                    cause = currentState.cause
                )
            }
        }.exhaustive
    }

    private fun reduce(event: JoinGroupUiEvent, currentState: JoinGroupUiState.WaitingGroupData) {
        when (event) {
            is JoinGroupUiEvent.BackClicked -> onBackClicked()
            is JoinGroupUiEvent.JoinGroup -> createNewGroup(currentState.groupCode)
            is JoinGroupUiEvent.UpdateGroupCode -> {
                val groupCode = getNewCode(event.code)
                _uiState.value = JoinGroupUiState.WaitingGroupData(
                    groupCode = groupCode,
                    isJoiningEnabled = isJoiningEnabled(groupCode)
                )
            }
        }.exhaustive
    }

    private fun isJoiningEnabled(groupCode: InputFiled): Boolean {
        return (groupCode.error == null) and
                groupCode.value.isNotEmpty()
    }

    private fun createNewGroup(groupCode: InputFiled) {
        viewModelScope.launch {
            groupRepository.joinToGroup(groupId = groupCode.value).result(
                onSuccess = { onJoinClicked() },
                onFailure = {
                    _uiState.value = JoinGroupUiState.FailedCreation(
                        groupCode = groupCode,
                        isJoiningEnabled = isJoiningEnabled(groupCode),
                        cause = it.error.readableCause()
                    )
                }
            )
        }
    }

    private fun getNewCode(code: String): InputFiled {
        return InputFiled(
            value = code,
            error = groupCodeLinter.check(code.trim())
        )
    }

}