package com.example.cubit.di

import android.app.Application
import androidx.room.Room
import com.example.feature_group.data.local.GroupDao
import com.example.feature_group.data.local.GroupDatabase
import com.example.feature_group.data.local.PostDao
import com.example.feature_group.data.local.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideGroupDatabase(
        app: Application
    ): GroupDatabase {
        return Room.databaseBuilder(
            app,
            GroupDatabase::class.java,
            "group_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGroupDao(
        groupDatabase: GroupDatabase
    ): GroupDao {
        return groupDatabase.dao
    }

    @Provides
    @Singleton
    fun providePostDatabase(
        app: Application
    ): PostDatabase {
        return Room.databaseBuilder(
            app,
            PostDatabase::class.java,
            "post_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providePostDao(
        postDatabase: PostDatabase
    ): PostDao {
        return postDatabase.dao
    }

}