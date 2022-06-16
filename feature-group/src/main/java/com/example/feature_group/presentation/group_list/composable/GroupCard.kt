package com.example.feature_group.presentation.group_list.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.presentation.theme.Typography
import com.example.feature_group.presentation.common.item.GroupItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupCard(
    modifier: Modifier = Modifier,
    group: GroupItem,
    onClick: (groupId: String) -> Unit
) {
    Card(
        onClick = { onClick(group.name) },
        modifier = modifier
            .fillMaxWidth()
            .height(144.dp),
        colors = CardDefaults.cardColors(
            containerColor = group.coverColor,
            contentColor = Color.White
        )
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            Text(
                text = group.name,
                style = Typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = group.description,
                style = Typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = group.ownerName,
                style = Typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupCardPreview() {
    GroupCard(
        group = GroupItem(
            name = "Group name",
            description = "Here is a description",
            ownerName = "Teacher Name",
            coverColor = Color.Blue
        ),
        onClick = {},
        modifier = Modifier.padding(8.dp)
    )
}