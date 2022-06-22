package com.example.feature_group.data.remote.entry

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feature_group.presentation.common.item.GroupItem

@Entity
data class GetUserGroupsResponseItem(
    val code: String,
    val color: String,
    val creationDate: String,
    val description: String,
    @PrimaryKey
    val id: String,
    val ownerColor: String,
    val ownerFirstName: String,
    val ownerId: String,
    val ownerLastName: String,
    val title: String
)

fun GetUserGroupsResponseItem.toGroupItem(): GroupItem {
    return GroupItem(
        id = this.id,
        ownerId = this.ownerId,
        name = this.title,
        description = this.description,
        code = this.code,
        ownerName = "${this.ownerFirstName} ${this.ownerLastName}",
        coverColor = Color(0xFF3B79E8)//Color(android.graphics.Color.parseColor(groupResponseItem.coverColor)) //TODO: set real color
    )
}