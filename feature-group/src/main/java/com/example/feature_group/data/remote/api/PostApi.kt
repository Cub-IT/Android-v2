package com.example.feature_group.data.remote.api

import com.example.core.util.Result
import com.example.feature_group.data.remote.entry.GetGroupPostsResponse
import retrofit2.http.GET

interface PostApi {

    @GET("/api/v1/group/25/posts")
    suspend fun getGroupPosts(): Result<GetGroupPostsResponse, Exception>

}