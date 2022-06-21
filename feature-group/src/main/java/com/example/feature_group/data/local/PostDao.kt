package com.example.feature_group.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feature_group.data.local.entity.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroupPosts(data: List<PostEntity>)

    @Query("SELECT * FROM postentity WHERE groupId = :groupId")
    fun getGroupPosts(groupId: String): Flow<List<PostEntity>>

    @Query("SELECT * FROM postentity WHERE groupId = :groupId")
    suspend fun getGroupPostsSuspend(groupId: String): List<PostEntity>

    @Query("DELETE FROM postentity WHERE groupId = :groupId")
    suspend fun deleteGroupPosts(groupId: String)

}