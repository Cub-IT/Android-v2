package com.example.feature_group.data.remote.api

import com.example.core.util.Result
import com.example.feature_group.data.remote.entry.CreateGroupRequest
import com.example.feature_group.data.remote.entry.GetUserGroupsResponse
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Path
import javax.inject.Inject

class GroupService @Inject constructor(
    private val retrofit: Retrofit
) {

    private val groupApi: GroupApi by lazy { retrofit.create(GroupApi::class.java) }

    suspend fun getUserGroups(): Result<GetUserGroupsResponse, Exception> {
        return groupApi.getUserGroups()
    }

    suspend fun joinGroup(groupCode: String): Result<Unit, Exception> {
        return groupApi.joinGroup(groupCode = groupCode)
    }

    suspend fun createGroup(title: String, description: String): Result<Unit, Exception> {
        val createGroupRequest = CreateGroupRequest(
            title = title,
            description = description
        )
        return groupApi.createGroup(createGroupRequest)
    }

}