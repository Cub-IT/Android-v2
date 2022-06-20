package com.example.feature_group.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.feature_group.data.remote.entry.GetUserGroupsResponseItem

@Database(entities = [GetUserGroupsResponseItem::class], version = 1)
abstract class GroupDatabase: RoomDatabase() {

    abstract val dao: GroupDao

}