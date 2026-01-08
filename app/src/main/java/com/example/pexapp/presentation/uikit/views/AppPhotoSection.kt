package com.example.pexapp.presentation.uikit.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.pexapp.presentation.model.PhotoModelUi
import com.example.pexapp.presentation.uikit.theme.appPhotoSectionSpacing
import com.example.pexapp.presentation.uikit.theme.appRoundedShape
import com.example.pexapp.presentation.uikit.theme.detailsScreenThemedPhotosLoadingPadding
import com.example.pexapp.presentation.uikit.utils.CommonValues

@Composable
fun AppPhotoSection(
    modifier: Modifier = Modifier,
    photos: List<PhotoModelUi>,
    isLoading: Boolean = false,
    onPhotoClicked: (PhotoModelUi) -> Unit = {},
    isSimple: Boolean = false,
    listState: LazyStaggeredGridState = rememberLazyStaggeredGridState()
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        state = listState,
        columns = StaggeredGridCells.Fixed(CommonValues.NUMBER_OF_COLUMNS),
        verticalItemSpacing = appPhotoSectionSpacing,
        horizontalArrangement = Arrangement.spacedBy(appPhotoSectionSpacing)
    ) {
        items(photos) { photo ->
            AppPhotoCard(
                modifier = Modifier
                    .clip(appRoundedShape)
                    .clickable { onPhotoClicked(photo) },
                photo = photo,
                isSimple = isSimple
            )
        }

        if (isLoading) {
            item {
                AppProgressBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(detailsScreenThemedPhotosLoadingPadding)
                )
            }
        }
    }
}