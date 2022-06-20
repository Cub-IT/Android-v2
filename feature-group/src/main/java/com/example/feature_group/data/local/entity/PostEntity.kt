package com.example.feature_group.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostEntity(
    @PrimaryKey
    val id: String,
    val groupId: String,
    val creationDate: String,
    val editDate: String,
    val description: String
)