package com.example.cubit.di

import android.app.Application
import androidx.room.Room
import com.example.feature_group.data.local.GroupDao
import com.example.feature_group.data.local.GroupDatabase
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

}