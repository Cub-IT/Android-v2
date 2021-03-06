package com.example.feature_group.presentation.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LetterAvatar(
    letter: Char?,
    color: Color
) {
    Surface(
        modifier = Modifier.size(24.dp),
        shape = CircleShape
    ) {
        Box(
            modifier = Modifier.background(color = color),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = letter?.toString() ?: "#",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@Preview
@Composable
fun LetterAvatarPreview() {
    LetterAvatar(letter = 'M', color = Color.Blue)
}