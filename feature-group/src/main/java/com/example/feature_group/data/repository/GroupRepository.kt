package com.example.feature_group.data.repository

import com.example.core.data.local.UserSource
import com.example.core.util.Result
import com.example.core.util.onFailure
import com.example.feature_group.data.local.GroupDao
import com.example.feature_group.data.local.PostDao
import com.example.feature_group.data.local.entity.PostEntity
import com.example.feature_group.data.remote.api.GroupService
import com.example.feature_group.data.remote.api.PostService
import com.example.feature_group.data.remote.entry.GetUserGroupsResponseItem
import com.example.feature_group.data.remote.entry.toGroupItem
import com.example.feature_group.presentation.common.item.GroupItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    suspend fun getUserGroupsSuspend(): List<GroupItem> {
        return groupDao.getUserGroupsSuspend().map { it.toGroupItem() }
    }

    fun getUserGroup(groupId: String): Flow<GroupItem> {
        return groupDao.getUserGroup(groupId = groupId).transformLatest { it.toGroupItem() }
    }

    suspend fun getUserGroupSuspend(groupId: String): GroupItem {
        return groupDao.getUserGroupSuspend(groupId = groupId).toGroupItem()
    }

    suspend fun updateUserGroups(): Result<Unit, Exception> {
        val groups = groupService.getUserGroups()
            .onFailure { return it }

        /*val groups = listOf(
            GetUserGroupsResponseItem(
                id = "1",
                title = "Title 1",
                description = "Description 1",
                code = "ABCD56789e",
                creationDate = "20.06.2022",
                ownerFirstName = "Mar",
                ownerLastName = "Yav"
            ),
            GetUserGroupsResponseItem(
                id = "2",
                title = "Title 2",
                description = "Description 2",
                code = "ABCD56789e",
                creationDate = "20.06.2022",
                ownerFirstName = "Mar",
                ownerLastName = "Yav"
            ),
            GetUserGroupsResponseItem(
                id = "3",
                title = "Title 3",
                description = "Description 3",
                code = "ABCD56789e",
                creationDate = "20.06.2022",
                ownerFirstName = "Mar",
                ownerLastName = "Yav"
            ),
            GetUserGroupsResponseItem(
                id = "4",
                title = "Title 4",
                description = "Description 4",
                code = "ABCD56789e",
                creationDate = "20.06.2022",
                ownerFirstName = "Mar",
                ownerLastName = "Yav"
            ),
            GetUserGroupsResponseItem(
                id = "5",
                title = "Title 5",
                description = "Description 5",
                code = "ABCD56789e",
                creationDate = "20.06.2022",
                ownerFirstName = "Mar",
                ownerLastName = "Yav"
            ),
            GetUserGroupsResponseItem(
                id = "6",
                title = "Title 6",
                description = "Description 6",
                code = "ABCD56789e",
                creationDate = "20.06.2022",
                ownerFirstName = "Mar",
                ownerLastName = "Yav"
            ),
        )*/

        groupDao.deleteUserGroups()
        groupDao.insertUserGroups(groups)

        return Result.Success(Unit)
    }

    fun getGroupPosts(groupId: String): Flow<List<PostEntity>> {
        return postDao.getGroupPosts(groupId = groupId)
    }

    suspend fun getGroupPostsSuspend(groupId: String): List<PostEntity> {
        return postDao.getGroupPostsSuspend(groupId = groupId)
    }

    suspend fun updateGroupPosts(groupId: String): Result<Unit, Exception> {
        val posts = postService.getGroupPosts(groupCode = groupId)
            .onFailure { return it }
            .map {
                PostEntity(
                    id = it.id,
                    groupId = groupId,
                    creationDate = it.creationDate,
                    editDate = it.editDate,
                    description = it.description
                )
            }

        postDao.deleteGroupPosts(groupId = groupId)
        postDao.insertGroupPosts(posts)

        return Result.Success(Unit)
    }

    suspend fun createGroup(name: String, description: String): Result<Unit, Exception> {
        return groupService.createGroup(title = name, description = description)
    }

    suspend fun joinToGroup(groupId: String): Result<Unit, Exception> {
        return groupService.joinGroup(groupCode = groupId)
    }

    suspend fun createPost(groupCode: String, content: String): Result<Unit, Exception> {
        return postService.createPost(groupCode = groupCode, description = content)
    }

    suspend fun updatePost(postId: String, content: String): Result<Unit, Exception> {
        return postService.updatePost(taskId = postId, description = content)
    }

    suspend fun getPost(postId: String): PostEntity {
        return postDao.getPost(postId = postId)
    }

    suspend fun logout() {
        userSource.deleteUser()
        groupDao.deleteUserGroups()
    }

}