package com.example.feature_group.presentation.group.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.presentation.theme.Typography
import com.example.feature_group.presentation.common.composable.IconAvatar
import com.example.feature_group.presentation.group.item.PostItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Task(
    task: PostItem,
    modifier: Modifier = Modifier
) {
    OutlinedCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconAvatar(color = task.creatorColor, size = 32.dp)
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = task.creatorName,
                        style = Typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = task.creationDate,
                        style = Typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = task.content,
                style = Typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskPreview() {
    Task(
        task = PostItem(
            creatorName = "Mark Yavorskiy",
            creatorColor = Color.Blue,
            creationDate = "May 16",
            content = "Запрошую Вас на Зум-конференцію."
        ),
        modifier = Modifier.padding(10.dp)
    )
}