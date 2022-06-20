package com.example.feature_group.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.feature_group.data.remote.entry.GetGroupPostsResponseItem

@Database(entities = [GetGroupPostsResponseItem::class], version = 1)
abstract class PostDatabase: RoomDatabase() {

    abstract val dao: PostDao

}
