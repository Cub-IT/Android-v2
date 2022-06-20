package com.example.feature_group.data.repository

import com.example.core.data.local.UserSource
import com.example.core.util.Result
import com.example.core.util.onFailure
import com.example.feature_group.data.local.GroupDao
import com.example.feature_group.data.remote.api.GroupService
import com.example.feature_group.data.remote.entry.toGroupItem
import com.example.feature_group.data.remote.entry.toGroupItemList
import com.example.feature_group.presentation.common.item.GroupItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.transformLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GroupRepository @Inject constructor(
    private val groupService: GroupService,
    private val dao: GroupDao,
    private val userSource: UserSource
) {

    fun getUserGroups(): Flow<List<GroupItem>> {
        return dao.getUserGroups().transformLatest { groupList ->
            groupList.map { it.toGroupItem() }
        }
    }

    suspend fun updateUserGroups(): Result<Unit, Exception> {
        val groups = groupService.getUserGroups()
            .onFailure { return it }

        dao.deleteUserGroups()
        dao.insertUserGroups(groups)

        return Result.Success(Unit)
    }

    suspend fun getGroup(groupId: String): Result<GroupItem, Exception> {
        TODO()
    }

    suspend fun createGroup(name: String, description: String): Result<Unit, Exception> {
        TODO()
    }

    suspend fun joinToGroup(groupId: String): Result<Unit, Exception> {
        TODO()
    }

    suspend fun createPost(content: String): Result<Unit, Exception> {
        TODO()
    }

    suspend fun logout() {
        userSource.deleteUser()
        dao.deleteUserGroups()
    }

}