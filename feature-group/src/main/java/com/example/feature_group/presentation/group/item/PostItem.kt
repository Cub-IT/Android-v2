package com.example.feature_group.presentation.group.item

import androidx.compose.ui.graphics.Color

data class PostItem(
    val id: String,
    val creatorName: String,
    val creatorColor: Color,
    val creationDate: String,
    val content: String,
    val isModified: Boolean
)
