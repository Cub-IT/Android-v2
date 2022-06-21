package com.example.feature_group.data.remote.api

import com.example.core.util.Result
import com.example.feature_group.data.remote.entry.CreatePostRequest
import com.example.feature_group.data.remote.entry.GetGroupPostsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostApi {

    @GET("{groupCode}/posts")
    suspend fun getGroupPosts(
        @Path("groupCode") groupCode: String
    ): Result<GetGroupPostsResponse, Exception>

    @POST("{groupCode}")
    suspend fun createPost(
        @Path("groupCode") groupCode: String,
        @Body createPostRequest: CreatePostRequest
    ): Result<Unit, Exception>

}