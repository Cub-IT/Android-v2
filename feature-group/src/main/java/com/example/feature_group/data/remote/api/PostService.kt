package com.example.feature_group.data.remote.api

import com.example.core.util.Result
import com.example.feature_group.data.remote.entry.GetGroupPostsResponse
import retrofit2.Retrofit
import javax.inject.Inject

class PostService @Inject constructor(
    private val retrofit: Retrofit
) {

    private val postApi: PostApi by lazy { retrofit.create(PostApi::class.java) }

    suspend fun getGroupPosts(): Result<GetGroupPostsResponse, Exception> {
        return postApi.getGroupPosts()
    }

}