package com.example.feature_group.presentation.group

import com.example.core.presentation.BaseViewModel
import com.example.feature_group.presentation.group_list.GroupListUiEvent
import com.example.feature_group.presentation.group_list.GroupListUiState
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GroupViewModel @AssistedInject constructor(

) : BaseViewModel<GroupListUiEvent, GroupListUiState>() {

    @AssistedFactory
    interface Factory {
        fun create(

        ): GroupViewModel
    }

    override fun createInitialState(): GroupListUiState {
        TODO("Not yet implemented")
    }

    override fun handleEvent(event: GroupListUiEvent) {
        TODO("Not yet implemented")
    }

}