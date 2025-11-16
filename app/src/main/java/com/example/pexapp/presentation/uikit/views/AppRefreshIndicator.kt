package com.example.pexapp.presentation.uikit.views

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.PositionalThreshold
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.pexapp.R
import com.example.pexapp.presentation.uikit.theme.appColor
import com.example.pexapp.presentation.uikit.theme.baseTextColor
import com.example.pexapp.presentation.uikit.theme.homeScreenIndicatorInnerPadding
import com.example.pexapp.presentation.uikit.theme.homeScreenIndicatorSize


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRefreshIndicator(
    modifier: Modifier = Modifier,
    state: PullToRefreshState,
    isRefreshing: Boolean,
    durationMillis: Int = 1200
) {

    val bgColor by animateColorAsState(
        targetValue = if (isRefreshing) baseTextColor else Color.Transparent,
        animationSpec = tween(durationMillis = durationMillis)
    )

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(bgColor)
            .padding(homeScreenIndicatorInnerPadding)
            .pullToRefresh(
                state = state,
                isRefreshing = isRefreshing,
                threshold = PositionalThreshold,
                onRefresh = {}
            ),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(
            modifier = Modifier.align(Alignment.Center),
            targetState = isRefreshing,
            animationSpec = tween(durationMillis = durationMillis)
        ) { refreshing ->
            if (refreshing) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(homeScreenIndicatorSize),
                    trackColor = appColor
                )
            } else {
                val distanceFraction = { state.distanceFraction.coerceIn(0f, 1f) }
                Icon(
                    modifier = Modifier
                        .size(homeScreenIndicatorSize)
                        .graphicsLayer {
                            val progress = distanceFraction()
                            this.alpha = progress
                            this.scaleX = progress
                            this.scaleY = progress
                        },
                    painter = painterResource(R.drawable.refresh_icon),
                    contentDescription = stringResource(R.string.picture_description),
                    tint = appColor
                )
            }
        }
    }
}