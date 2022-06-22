package com.example.feature_group.data.remote.api

import com.example.core.util.Result
import com.example.feature_group.data.remote.entry.CreatePostRequest
import com.example.feature_group.data.remote.entry.GetGroupPostsResponse
import retrofit2.http.*

interface PostApi {

    @GET("{groupCode}/posts")
    suspend fun getGroupPosts(
        @Path("groupCode") groupCode: String
    ): Result<GetGroupPostsResponse, Exception>

    @POST("new/group/{groupCode}")
    suspend fun createPost(
        @Path("groupCode") groupCode: String,
        @Body createPostRequest: CreatePostRequest
    ): Result<Unit, Exception>

    @PATCH("{taskId}/edit")
    suspend fun updatePost(
        @Path("taskId") taskId: String,
        @Body createPostRequest: CreatePostRequest
    ): Result<Unit, Exception>

}