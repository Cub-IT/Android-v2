package com.example.cubit.di

import com.example.core.data.local.UserSource
import com.example.core.data.remote.ResultAdapterFactory
import com.example.core.util.EXTENDED_API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(userSource: UserSource, okHttpClient: OkHttpClient): Retrofit {
        val baseUrl = when (userSource.isAuthorized()) {
            true -> {
                val userId = userSource.getUser()?.id
                    ?: throw IllegalStateException("User is authorized but user's id is ${userSource.getUser()?.id}")
                EXTENDED_API_URL.plus("$userId/")
            }
            false -> EXTENDED_API_URL
        }
        return createRetrofit(baseUrl, okHttpClient).build()
    }

    private fun createRetrofit(url: String, okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addCallAdapterFactory(ResultAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .cookieJar(UvCookieJar())
            .build()
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private class UvCookieJar : CookieJar {

        private val cookies = mutableListOf<Cookie>()
        
        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            this.cookies.clear()
            this.cookies.addAll(cookies)
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> =
            cookies
    }
}
