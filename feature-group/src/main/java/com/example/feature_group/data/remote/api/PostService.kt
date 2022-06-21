package com.example.feature_group.data.remote.api

import com.example.core.util.Result
import com.example.feature_group.data.remote.entry.GetGroupPostsResponse
import retrofit2.Retrofit
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Named

class PostService @Inject constructor(
    @Named("postRetrofit") private val retrofit: Retrofit
) {

    private val postApi: PostApi by lazy { retrofit.create(PostApi::class.java) }

    suspend fun getGroupPosts(groupCode: String): Result<GetGroupPostsResponse, Exception> {
        return postApi.getGroupPosts(groupCode = groupCode)
    }

}