package com.example.feature_group.presentation.common.item

import androidx.compose.ui.graphics.Color

data class GroupItem(
    val id: String,
    val ownerId: String,
    val name: String,
    val description: String,
    val code: String,
    val ownerName: String,
    val coverColor: Color
)
