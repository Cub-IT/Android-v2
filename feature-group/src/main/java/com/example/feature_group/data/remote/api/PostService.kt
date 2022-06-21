package com.example.feature_group.data.remote.api

import com.example.core.util.Result
import com.example.feature_group.data.remote.entry.CreatePostRequest
import com.example.feature_group.data.remote.entry.GetGroupPostsResponse
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class PostService @Inject constructor(
    @Named("postRetrofit") private val postRetrofit: Retrofit,
    @Named("taskRetrofit") private val taskRetrofit: Retrofit,
) {

    private val postApi: PostApi by lazy { postRetrofit.create(PostApi::class.java) }
    private val taskApi: PostApi by lazy { taskRetrofit.create(PostApi::class.java) }

    suspend fun getGroupPosts(groupCode: String): Result<GetGroupPostsResponse, Exception> {
        return postApi.getGroupPosts(groupCode = groupCode)
    }

    suspend fun createPost(
        groupCode: String,
        description: String
    ): Result<Unit, Exception> {
        return taskApi.createPost(groupCode, CreatePostRequest(description))
    }

}