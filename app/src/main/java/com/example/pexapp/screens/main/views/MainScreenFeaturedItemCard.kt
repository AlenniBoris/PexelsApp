package com.example.pexapp.screens.main.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FeaturedItem(
    title: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val background =
        if (selected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary

    val textColor = if (selected) Color.White else MaterialTheme.colorScheme.onBackground

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(background)
            .clickable { onClick() },
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            text = title,
            color = textColor,
            fontSize = 14.sp
        )
    }
}