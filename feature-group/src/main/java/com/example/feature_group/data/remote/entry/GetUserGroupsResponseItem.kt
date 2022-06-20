package com.example.feature_group.data.remote.entry

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feature_group.presentation.common.item.GroupItem

@Entity
data class GetUserGroupsResponseItem(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val code: String,
    val creationDate: String,
    val ownerFirstName: String,
    val ownerLastName: String

    /*val coverColor: String,
    val description: String,
    val groupId: String,
    val name: String,
    val ownerEmail: String,
    val ownerFirstName: String,
    val ownerLastName: String*/

    /*@Entity
data class GroupEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val code: String,
    val ownerName: String,
    val coverColor: Color*
)*/

)

fun GetUserGroupsResponseItem.toGroupItem(): GroupItem {
    return GroupItem(
        id = this.id,
        name = this.title,
        description = this.description,
        ownerName = "${this.ownerFirstName} ${this.ownerLastName}",
        coverColor = Color.Magenta//Color(android.graphics.Color.parseColor(groupResponseItem.coverColor)) //TODO: set real color
    )
}