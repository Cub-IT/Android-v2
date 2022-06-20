package com.example.feature_group.data.remote.entry

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feature_group.presentation.group.item.PostItem

data class GetGroupPostsResponseItem (
    val id: String,
    val creationDate: String,
    val editDate: String,
    val description: String
)

/*fun GetGroupPostsResponseItem.toPostItem(): PostItem {
    return PostItem(
        creatorName = "Creator name",
        creatorColor = Color.Magenta,
        creationDate = this.creationDate,
        content = this.description,
    )
}*/