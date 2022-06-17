package com.example.cubit.di

import com.example.core.data.local.UserSource
import com.example.feature_auth.data.local.UserDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Binds
    @Singleton
    abstract fun provideUserSource(userDataStore: UserDataStore): UserSource

}