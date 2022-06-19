package com.example.cubit.di

import com.example.core.data.local.UserSource
import com.example.core.data.remote.ResultAdapterFactory
import com.example.core.util.EXTENDED_API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(userSource: UserSource): Retrofit {
        val baseUrl = when (userSource.isAuthorized()) {
            true -> {
                val userId = userSource.getUser()?.id
                    ?: throw IllegalStateException("User is authorized but user's id is ${userSource.getUser()?.id}")
                EXTENDED_API_URL.plus("$userId/")
            }
            false -> EXTENDED_API_URL
        }
        return createRetrofit(baseUrl).build()
    }

    private fun createRetrofit(url: String) = Retrofit.Builder()
        .baseUrl(url)
        .client(createOkHttpClient())
        .addCallAdapterFactory(ResultAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}
