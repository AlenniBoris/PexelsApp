package com.example.pexapp.presentation.uikit.utils

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.shimmer(
    cornerRadius: Dp = 0.dp,
    startColor: Color = Color.Blue,
    middleColor: Color = Color.Green,
    endColor: Color = Color.Red,
    animationTime: Int = 2000
): Modifier {
    val shimmerColors = listOf(
        endColor.copy(alpha = 0.5f),
        middleColor.copy(alpha = 0.7f),
        startColor.copy(alpha = 0.5f)
    )

    val transition = rememberInfiniteTransition(label = "Shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = -400f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationTime, // slower = smoother
                easing = FastOutSlowInEasing // smoother easing
            )
        ),
        label = "Translate"
    )

    return this.drawWithCache {
        val brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(translateAnim, 0f),
            // wider gradient
            end = Offset(translateAnim + size.width / 1.5f, size.height)
        )
        val cornerPx = cornerRadius.toPx()
        onDrawWithContent {
            drawRoundRect(
                brush = brush,
                cornerRadius = CornerRadius(cornerPx, cornerPx),
                size = size
            )
        }
    }
}

@Composable
fun Modifier.shimmer(
    cornerRadius: Dp = 0.dp,
    isLoading: Boolean,
    startColor: Color = Color.Blue,
    middleColor: Color = Color.Green,
    endColor: Color = Color.Red,
    animationTime: Int = 2000
): Modifier {
    return if (isLoading)
        this.shimmer(
            cornerRadius = cornerRadius,
            startColor = startColor,
            middleColor = middleColor,
            endColor = endColor,
            animationTime = animationTime
        ) else this
}