package com.example.feature_group.presentation.add_group

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.BaseViewModel
import com.example.core.presentation.item.InputFiled
import com.example.core.util.InputLinter
import com.example.core.util.readableCause
import com.example.core.util.exhaustive
import com.example.core.util.result
import com.example.feature_group.data.repository.GroupRepository
import com.example.feature_group.presentation.add_group.item.NewGroupItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import javax.inject.Named

class AddGroupViewModel @AssistedInject constructor(
    @Assisted("create") private val onCreateClicked: () -> Unit,
    @Assisted("back") private val onBackClicked: () -> Unit,
    private val groupRepository: GroupRepository,
    @Named("longNameLinter") private val longNameLinter: InputLinter
) : BaseViewModel<AddGroupUiEvent, AddGroupUiState>() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("create") onCreateClicked: () -> Unit,
            @Assisted("back") onBackClicked: () -> Unit
        ): AddGroupViewModel
    }

    override fun createInitialState(): AddGroupUiState {
        val group = NewGroupItem(
            name = InputFiled(""),
            description = InputFiled("")
        )
        return AddGroupUiState.WaitingGroupData(
            group = group,
            isCreationEnabled = isCreationEnabled(group)
        )
    }

    override fun handleEvent(event: AddGroupUiEvent) {
        when (val currentState = uiState.value) {
            is AddGroupUiState.FailedCreation -> reduce(event, currentState)
            is AddGroupUiState.WaitingGroupData -> reduce(event, currentState)
            is AddGroupUiState.WaitingResponse -> throw IllegalStateException()
        }.exhaustive
    }

    private fun reduce(event: AddGroupUiEvent, currentState: AddGroupUiState.FailedCreation) {
        when (event) {
            is AddGroupUiEvent.BackClicked -> onBackClicked()
            is AddGroupUiEvent.CreateGroup -> createNewGroup(currentState.group)
            is AddGroupUiEvent.UpdateGroupDescription -> {
                val groupInput = NewGroupItem(
                    name = currentState.group.name,
                    description = InputFiled(event.description)
                )
                _uiState.value = AddGroupUiState.FailedCreation(
                    group = groupInput,
                    isCreationEnabled = isCreationEnabled(groupInput),
                    cause = currentState.cause
                )
            }
            is AddGroupUiEvent.UpdateGroupName -> {
                val groupInput = NewGroupItem(
                    name = getNewName(event.name),
                    description = currentState.group.description
                )
                _uiState.value = AddGroupUiState.FailedCreation(
                    group = groupInput,
                    isCreationEnabled = isCreationEnabled(groupInput),
                    cause = currentState.cause
                )
            }
        }.exhaustive
    }

    private fun reduce(event: AddGroupUiEvent, currentState: AddGroupUiState.WaitingGroupData) {
        when (event) {
            is AddGroupUiEvent.BackClicked -> onBackClicked()
            is AddGroupUiEvent.CreateGroup -> createNewGroup(currentState.group)
            is AddGroupUiEvent.UpdateGroupDescription -> {
                val groupInput = NewGroupItem(
                    name = currentState.group.name,
                    description = InputFiled(event.description)
                )
                _uiState.value = AddGroupUiState.WaitingGroupData(
                    group = groupInput,
                    isCreationEnabled = isCreationEnabled(groupInput)
                )
            }
            is AddGroupUiEvent.UpdateGroupName -> {
                val groupInput = NewGroupItem(
                    name = getNewName(event.name),
                    description = currentState.group.description
                )
                _uiState.value = AddGroupUiState.WaitingGroupData(
                    group = groupInput,
                    isCreationEnabled = isCreationEnabled(groupInput)
                )
            }
        }.exhaustive
    }

    private fun isCreationEnabled(group: NewGroupItem): Boolean {
        return (group.name.error == null) and
                group.name.value.isNotEmpty()
    }

    private fun createNewGroup(group: NewGroupItem) {
        viewModelScope.launch {
            groupRepository.createGroup(
                name = group.name.value,
                description = group.description.value
            ).result(
                onSuccess = { onCreateClicked() },
                onFailure = {
                    _uiState.value = AddGroupUiState.FailedCreation(
                        group = group,
                        isCreationEnabled = isCreationEnabled(group),
                        cause = it.error.readableCause()
                    )
                }
            )
        }
    }

    private fun getNewName(name: String): InputFiled {
        return InputFiled(
            value = name,
            error = longNameLinter.check(name.trim())
        )
    }

}