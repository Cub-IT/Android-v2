package com.example.feature_group.data.remote.api

import com.example.core.util.Result
import com.example.feature_group.data.remote.entry.GetUserGroupsResponse
import retrofit2.http.GET

interface GroupApi {

    @GET("/api/v1/user/:userId/groups")
    suspend fun getUserGroups(): Result<GetUserGroupsResponse, Exception>

}