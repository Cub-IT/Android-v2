package com.example.feature_group.presentation.group.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.presentation.theme.Typography
import com.example.feature_group.presentation.common.item.GroupItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupHeaderCard(
    modifier: Modifier = Modifier,
    group: GroupItem
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(128.dp),
        colors = CardDefaults.cardColors(
            containerColor = group.coverColor,
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
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
    }
}

/*@Preview(showBackground = true)
@Composable
fun GroupHeaderCardPreview() {
    GroupHeaderCard(
        group = GroupItem(
            name = "Group name",
            description = "Here is a description",
            ownerName = "Teacher Name 5",
            coverColor = Color.Blue
        ),
        modifier = Modifier.padding(8.dp)
    )
}*/