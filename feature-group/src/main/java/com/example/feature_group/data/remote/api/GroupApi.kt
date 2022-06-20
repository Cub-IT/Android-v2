package com.example.feature_group.data.remote.api

import com.example.core.util.Result
import com.example.feature_group.data.remote.entry.CreateGroupRequest
import com.example.feature_group.data.remote.entry.GetUserGroupsResponse
import retrofit2.http.*

interface GroupApi {

    @GET("groups")
    suspend fun getUserGroups(): Result<GetUserGroupsResponse, Exception>

    @PATCH("join/{groupCode}")
    suspend fun joinGroup(
        @Path("groupCode") groupCode: String
    ): Result<Unit, Exception>

    @POST("/cub-it/rest/api/v1/group/new")
    suspend fun createGroup(
        @Body createGroupRequest: CreateGroupRequest
    ): Result<Unit, Exception>

}