package com.example.feature_group.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feature_group.data.remote.entry.GetUserGroupsResponseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserGroups(data: List<GetUserGroupsResponseItem>)

    @Query("SELECT * FROM getusergroupsresponseitem")
    fun getUserGroups(): Flow<List<GetUserGroupsResponseItem>>

    @Query("DELETE FROM getusergroupsresponseitem")
    suspend fun deleteUserGroups()

}