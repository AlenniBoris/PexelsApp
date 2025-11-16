package com.example.pexapp.presentation.screens.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pexapp.R
import com.example.pexapp.domain.model.CollectionsModelDomain
import com.example.pexapp.presentation.model.FeaturedCollectionModelUi
import com.example.pexapp.presentation.screens.home.HomeScreenValues
import com.example.pexapp.presentation.screens.home.IHomeScreenIntent
import com.example.pexapp.presentation.uikit.theme.PexAppTheme
import com.example.pexapp.presentation.uikit.theme.appColor
import com.example.pexapp.presentation.uikit.theme.appTextSize
import com.example.pexapp.presentation.uikit.theme.appTextStyle
import com.example.pexapp.presentation.uikit.theme.baseTextColor
import com.example.pexapp.presentation.uikit.theme.clickableElementBackground
import com.example.pexapp.presentation.uikit.theme.extraTextColor
import com.example.pexapp.presentation.uikit.theme.featuredItemActiveBackgroundColor
import com.example.pexapp.presentation.uikit.theme.featuredItemActiveTextColor
import com.example.pexapp.presentation.uikit.theme.homeScreenFeaturedEmptyPadding
import com.example.pexapp.presentation.uikit.theme.homeScreenFeaturedHorizontalSpacing
import com.example.pexapp.presentation.uikit.theme.homeScreenFeaturedItemInnerLoadingPadding
import com.example.pexapp.presentation.uikit.theme.homeScreenFeaturedItemInnerPadding
import com.example.pexapp.presentation.uikit.theme.homeScreenFeaturedItemShape
import com.example.pexapp.presentation.uikit.utils.shimmer

@Composable
fun HomeScreenFeaturedCollectionsSection(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    isLoading: Boolean,
    featured: List<FeaturedCollectionModelUi>,
    selectedFeatured: FeaturedCollectionModelUi?,
    proceedIntent: (IHomeScreenIntent) -> Unit
) {

    when {
        !isLoading && featured.isEmpty() -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(homeScreenFeaturedEmptyPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            proceedIntent(IHomeScreenIntent.RetryFeaturedCollectionsRequest)
                        },
                    painter = painterResource(R.drawable.refresh_icon),
                    contentDescription = stringResource(R.string.picture_description),
                    tint = extraTextColor
                )
            }
        }

        else -> {
            LazyRow(
                modifier = modifier,
                state = state,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(homeScreenFeaturedHorizontalSpacing)
            ) {
                items(featured, key = { item -> item.id }) { item ->
                    val isActive = item == selectedFeatured
                    FeaturedItemUi(
                        modifier = Modifier
                            .animateItem()
                            .clip(homeScreenFeaturedItemShape)
                            .shimmer(
                                isLoading = isLoading,
                                startColor = clickableElementBackground,
                                middleColor = baseTextColor,
                                endColor = clickableElementBackground
                            )
                            .background(
                                if (isActive) featuredItemActiveBackgroundColor
                                else clickableElementBackground
                            )
                            .padding(
                                if (isLoading) homeScreenFeaturedItemInnerLoadingPadding
                                else homeScreenFeaturedItemInnerPadding
                            )
                            .clickable {
                                proceedIntent(
                                    IHomeScreenIntent.SelectFeaturedCollection(item)
                                )
                            },
                        item = item,
                        isActive = isActive
                    )
                }
            }
        }
    }
}


@Composable
private fun FeaturedItemUi(
    modifier: Modifier = Modifier,
    item: FeaturedCollectionModelUi,
    isActive: Boolean = false
) {
    Box(
        modifier = modifier
    ) {
        Text(
            text = item.text,
            style = appTextStyle.copy(
                color = if (isActive) featuredItemActiveTextColor
                else baseTextColor,
                fontSize = appTextSize
            )
        )
    }
}

@Composable
@Preview
private fun LightTheme() {
    PexAppTheme(
        darkTheme = false
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
                    .padding(horizontal = 20.dp)
            ) {
                HomeScreenFeaturedCollectionsSection(
                    modifier = Modifier.fillMaxWidth(),
                    isLoading = true,
                    featured = HomeScreenValues.DEFAULT_FEATURED_LIST,
                    selectedFeatured = null,
                    proceedIntent = {}
                )

                HomeScreenFeaturedCollectionsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    isLoading = false,
                    featured = emptyList(),
                    selectedFeatured = null,
                    proceedIntent = {}
                )

                HomeScreenFeaturedCollectionsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    isLoading = false,
                    featured = listOf(
                        FeaturedCollectionModelUi(
                            domainModel = CollectionsModelDomain(
                                id = 1.toString(),
                                title = "11",
                                description = "",
                                private = false,
                                mediaCount = 1,
                                photosCount = 1,
                                videosCount = 1
                            )
                        ),
                        FeaturedCollectionModelUi(
                            domainModel = CollectionsModelDomain(
                                id = 2.toString(),
                                title = "22",
                                description = "",
                                private = false,
                                mediaCount = 2,
                                photosCount = 2,
                                videosCount = 2
                            )
                        ),
                        FeaturedCollectionModelUi(
                            domainModel = CollectionsModelDomain(
                                id = 3.toString(),
                                title = "33",
                                description = "",
                                private = false,
                                mediaCount = 3,
                                photosCount = 3,
                                videosCount = 3
                            )
                        )
                    ),
                    selectedFeatured = null,
                    proceedIntent = {}
                )
            }
        }
    }
}

@Composable
@Preview
private fun DarkTheme() {
    PexAppTheme(
        darkTheme = true
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
                    .padding(horizontal = 20.dp)
            ) {
                HomeScreenFeaturedCollectionsSection(
                    modifier = Modifier.fillMaxWidth(),
                    isLoading = true,
                    featured = HomeScreenValues.DEFAULT_FEATURED_LIST,
                    selectedFeatured = null,
                    proceedIntent = {}
                )

                HomeScreenFeaturedCollectionsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    isLoading = false,
                    featured = emptyList(),
                    selectedFeatured = null,
                    proceedIntent = {}
                )

                HomeScreenFeaturedCollectionsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    isLoading = false,
                    featured = listOf(
                        FeaturedCollectionModelUi(
                            domainModel = CollectionsModelDomain(
                                id = 1.toString(),
                                title = "11",
                                description = "",
                                private = false,
                                mediaCount = 1,
                                photosCount = 1,
                                videosCount = 1
                            )
                        ),
                        FeaturedCollectionModelUi(
                            domainModel = CollectionsModelDomain(
                                id = 2.toString(),
                                title = "22",
                                description = "",
                                private = false,
                                mediaCount = 2,
                                photosCount = 2,
                                videosCount = 2
                            )
                        ),
                        FeaturedCollectionModelUi(
                            domainModel = CollectionsModelDomain(
                                id = 3.toString(),
                                title = "33",
                                description = "",
                                private = false,
                                mediaCount = 3,
                                photosCount = 3,
                                videosCount = 3
                            )
                        )
                    ),
                    selectedFeatured = null,
                    proceedIntent = {}
                )
            }
        }
    }
}