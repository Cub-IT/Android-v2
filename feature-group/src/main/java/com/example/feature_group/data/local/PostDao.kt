package com.example.feature_group.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feature_group.data.remote.entry.GetGroupPostsResponseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserGroups(data: List<GetGroupPostsResponseItem>)

    @Query("SELECT * FROM getgrouppostsresponseitem")
    fun getUserGroups(): Flow<List<GetGroupPostsResponseItem>>

    @Query("DELETE FROM getgrouppostsresponseitem")
    suspend fun deleteUserGroups()

}