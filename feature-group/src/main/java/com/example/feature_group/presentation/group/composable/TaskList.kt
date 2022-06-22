package com.example.feature_group.presentation.group.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feature_group.presentation.common.item.GroupItem
import com.example.feature_group.presentation.group.item.PostItem

@Composable
fun TaskList(
    group: GroupItem,
    tasks: List<PostItem>,
    modifier: Modifier = Modifier,
    onEditClick: (postId: String) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        item {
            GroupHeaderCard(
                group = group,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        items(tasks) { task ->
            Task(
                task = task,
                modifier = Modifier.padding(vertical = 4.dp),
                onEditClick = onEditClick
            )
        }
    }
}

/*
@Preview
@Composable
fun TaskListPreview() {
    TaskList(
        group = GroupItem(
            name = "Group name",
            description = "Here is a description",
            ownerName = "Teacher Name 5",
            coverColor = Color.Blue
        ),
        tasks = listOf(
            PostItem(
                creatorName = "Mark Yavorskiy",
                creatorColor = Color.Blue,
                creationDate = "May 16",
                content = "Запрошую Вас на Зум-конференцію."
            ),
            PostItem(
                creatorName = "Mark Yavorskiy",
                creatorColor = Color.Blue,
                creationDate = "May 16",
                content = "Запрошую Вас на Зум-конференцію."
            ),
            PostItem(
                creatorName = "Mark Yavorskiy",
                creatorColor = Color.Blue,
                creationDate = "May 16",
                content = "Запрошую Вас на Зум-конференцію."
            ),
            PostItem(
                creatorName = "Mark Yavorskiy",
                creatorColor = Color.Blue,
                creationDate = "May 16",
                content = "Запрошую Вас на Зум-конференцію."
            ),
            PostItem(
                creatorName = "Mark Yavorskiy",
                creatorColor = Color.Blue,
                creationDate = "May 16",
                content = "Запрошую Вас на Зум-конференцію."
            ),
            PostItem(
                creatorName = "Mark Yavorskiy",
                creatorColor = Color.Blue,
                creationDate = "May 16",
                content = "Запрошую Вас на Зум-конференцію."
            ),
            PostItem(
                creatorName = "Mark Yavorskiy",
                creatorColor = Color.Blue,
                creationDate = "May 16",
                content = "Запрошую Вас на Зум-конференцію."
            )
        )
    )
}*/
