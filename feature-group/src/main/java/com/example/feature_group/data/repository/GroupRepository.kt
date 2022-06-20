package com.example.feature_group.data.repository

import com.example.core.data.local.UserSource
import com.example.core.util.Result
import com.example.core.util.onFailure
import com.example.feature_group.data.local.GroupDao
import com.example.feature_group.data.local.PostDao
import com.example.feature_group.data.remote.api.GroupService
import com.example.feature_group.data.remote.api.PostService
import com.example.feature_group.data.remote.entry.toGroupItem
import com.example.feature_group.data.remote.entry.toPostItem
import com.example.feature_group.presentation.common.item.GroupItem
import com.example.feature_group.presentation.group.item.PostItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transformLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GroupRepository @Inject constructor(
    private val groupService: GroupService,
    private val postService: PostService,
    private val groupDao: GroupDao,
    private val postDao: PostDao,
    private val userSource: UserSource
) {

    fun getUserGroups(): Flow<List<GroupItem>> {
        return groupDao.getUserGroups().transformLatest { groupList ->
            groupList.map { it.toGroupItem() }
        }
    }

    suspend fun updateUserGroups(): Result<Unit, Exception> {
        val groups = groupService.getUserGroups()
            .onFailure { return it }

        groupDao.deleteUserGroups()
        groupDao.insertUserGroups(groups)

        return Result.Success(Unit)
    }

    fun getGroupPosts(groupId: String): Flow<List<PostItem>> {
        return postDao.getUserGroups().transformLatest { postList ->
            postList.map { it.toPostItem() }
        }
    }

    suspend fun updateGroupPosts(groupId: String): Result<Unit, Exception> {
        val posts = postService.getGroupPosts()
            .onFailure { return it }

        postDao.deleteUserGroups()
        postDao.insertUserGroups(posts)

        return Result.Success(Unit)
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
        groupDao.deleteUserGroups()
    }

}