package com.example.feature_group.data.remote.entry

import androidx.compose.ui.graphics.Color
import com.example.feature_group.presentation.common.item.GroupItem

class GetUserGroupsResponse : ArrayList<GetUserGroupsResponseItem>()

fun GetUserGroupsResponse.toGroupItemList(): List<GroupItem> {
    return this.map { groupResponseItem ->
        GroupItem(
            name = groupResponseItem.name,
            description = groupResponseItem.description,
            ownerName = "${groupResponseItem.ownerFirstName} ${groupResponseItem.ownerLastName}",
            coverColor = Color(android.graphics.Color.parseColor(groupResponseItem.coverColor))
        )
    }
}