package com.example.feature_group.data.repository

import com.example.core.util.Result
import com.example.core.util.onFailure
import com.example.feature_group.data.remote.api.GroupService
import com.example.feature_group.data.remote.entry.toGroupItemList
import com.example.feature_group.presentation.common.item.GroupItem
import javax.inject.Inject

class GroupRepository @Inject constructor(
    private val groupService: GroupService
) {

    suspend fun getUserGroups(): Result<List<GroupItem>, Exception> {
        val groups = groupService.getUserGroups()
            .onFailure { return it }
            .toGroupItemList()

        return Result.Success(groups)
    }

}