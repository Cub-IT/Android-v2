package com.example.feature_group.data.remote.entry

data class GetUserGroupsResponseItem(
    val coverColor: String,
    val description: String,
    val groupId: String,
    val name: String,
    val ownerEmail: String,
    val ownerFirstName: String,
    val ownerLastName: String
)