package com.example.feature_group.data.remote.entry

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feature_group.presentation.group.item.PostItem

@Entity
data class GetGroupPostsResponseItem (
    @PrimaryKey
    val id: String,
    val content: String
)

fun GetGroupPostsResponseItem.toPostItem(): PostItem {
    return PostItem(
        creatorName = "Creator name",
        creatorColor = Color.Magenta,
        creationDate = "creation date",
        content = this.content,
    )
}