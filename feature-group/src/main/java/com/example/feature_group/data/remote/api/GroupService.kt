package com.example.feature_group.data.remote.api

import com.example.core.util.Result
import com.example.feature_group.data.remote.entry.GetUserGroupsResponse
import retrofit2.Retrofit
import javax.inject.Inject

class GroupService @Inject constructor(
    private val retrofit: Retrofit
) {

    private val groupApi: GroupApi by lazy { retrofit.create(GroupApi::class.java) }

    suspend fun getUserGroups(): Result<GetUserGroupsResponse, Exception> {
        return groupApi.getUserGroups()
    }

}