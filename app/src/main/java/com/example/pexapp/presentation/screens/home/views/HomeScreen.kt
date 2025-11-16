package com.example.pexapp.presentation.screens.home.views

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.pexapp.R
import com.example.pexapp.presentation.navigation.AppScreen
import com.example.pexapp.presentation.screens.home.HomeScreenState
import com.example.pexapp.presentation.screens.home.HomeScreenValues
import com.example.pexapp.presentation.screens.home.HomeScreenViewModel
import com.example.pexapp.presentation.screens.home.IHomeScreenEvent
import com.example.pexapp.presentation.screens.home.IHomeScreenIntent
import com.example.pexapp.presentation.uikit.theme.PexAppTheme
import com.example.pexapp.presentation.uikit.theme.appColor
import com.example.pexapp.presentation.uikit.theme.appContentPadding
import com.example.pexapp.presentation.uikit.theme.homeScreenSectionTopPadding
import com.example.pexapp.presentation.uikit.theme.loadingProgressIndicatorHeight
import com.example.pexapp.presentation.uikit.theme.progressIndicatorColor
import com.example.pexapp.presentation.uikit.views.AppEmptyScreen
import com.example.pexapp.presentation.uikit.views.AppExceptionScreen
import com.example.pexapp.presentation.uikit.views.AppPhotoSection
import com.example.pexapp.presentation.uikit.views.AppRefreshIndicator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val context = LocalContext.current

    val viewModel: HomeScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val scrollEvent by remember { mutableStateOf(viewModel.scrollEvent) }

    var toast by remember {
        mutableStateOf(Toast.makeText(context, "", Toast.LENGTH_SHORT))
    }

    LaunchedEffect(event) {
        launch {
            event.filterIsInstance<IHomeScreenEvent.OpenPicture>().collect { coming ->
                navController.navigate(AppScreen.Details.route + coming.pictureId)
            }
        }
        launch {
            event.filterIsInstance<IHomeScreenEvent.ShowMessage>().collect { coming ->
                toast?.cancel()
                toast = Toast.makeText(
                    context,
                    context.getString(coming.messageId),
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }

    HomeScreenUi(
        state = state,
        scrollEvent = scrollEvent,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun HomeScreenUi(
    state: HomeScreenState,
    scrollEvent: Flow<Unit>,
    proceedIntent: (IHomeScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        HomeScreenSearchBar(
            modifier = Modifier
                .fillMaxWidth(),
            query = state.query,
            history = state.workingHistory,
            isSearchHistoryVisible = state.isHistoryVisible,
            placeholder = stringResource(R.string.enter_query_text),
            proceedIntent = proceedIntent
        )

        val featuredListState = rememberLazyListState()
        LaunchedEffect(scrollEvent) {
            scrollEvent
                .onEach { featuredListState.animateScrollToItem(0) }
                .launchIn(this)
        }
        HomeScreenFeaturedCollectionsSection(
            modifier = Modifier
                .padding(homeScreenSectionTopPadding)
                .fillMaxWidth()
                .padding(appContentPadding),
            state = featuredListState,
            isLoading = state.isFeaturedLoading,
            featured = if (!state.isFeaturedLoading) state.featuredCollections else HomeScreenValues.DEFAULT_FEATURED_LIST,
            selectedFeatured = state.selectedFeaturedCollection,
            proceedIntent = proceedIntent
        )

        if (state.isPhotosLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(homeScreenSectionTopPadding)
                    .height(loadingProgressIndicatorHeight),
                color = progressIndicatorColor,
                backgroundColor = appColor
            )
        }

        when {
            state.exception != null && state.photos.isEmpty() -> {
                AppExceptionScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(homeScreenSectionTopPadding)
                        .padding(appContentPadding)
                        .verticalScroll(rememberScrollState()),
                    exception = state.exception,
                    onTryAgain = {
                        proceedIntent(
                            IHomeScreenIntent.RetryPhotosRequest
                        )
                    }
                )
            }

            else -> {

                when {
                    !state.isPhotosLoading && state.photos.isEmpty() -> {
                        AppEmptyScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(homeScreenSectionTopPadding)
                                .padding(appContentPadding),
                            onExploreClicked = {
                                proceedIntent(
                                    IHomeScreenIntent.RetryPhotosRequest
                                )
                            }
                        )
                    }

                    state.photos.isNotEmpty() -> {

                        val refreshState = rememberPullToRefreshState()
                        PullToRefreshBox(
                            modifier = Modifier.fillMaxSize(),
                            isRefreshing = state.isRefreshing,
                            onRefresh = {
                                proceedIntent(
                                    IHomeScreenIntent.RefreshData
                                )
                            },
                            state = refreshState,
                            indicator = {
                                AppRefreshIndicator(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter),
                                    state = refreshState,
                                    isRefreshing = state.isRefreshing
                                )
                            }
                        ) {

                            val listState = rememberLazyStaggeredGridState()
                            LaunchedEffect(listState, state.photos) {
                                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                                    .distinctUntilChanged()
                                    .collect { ind ->
                                        ind?.let {
                                            if (
                                                (it >= state.photos.size - HomeScreenValues.LOADING_BOUNDARY_LIMIT)
                                                && state.isLoadingMorePossible
                                            ) {
                                                proceedIntent(IHomeScreenIntent.LoadMoreData)
                                            }
                                        }
                                    }
                            }

                            AppPhotoSection(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(homeScreenSectionTopPadding)
                                    .padding(appContentPadding),
                                photos = state.photos,
                                listState = listState,
                                onPhotoClicked = { photo ->
                                    proceedIntent(
                                        IHomeScreenIntent.OpenPhotoDetails(photo)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
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
                    .background(Color.Red)
                    .padding(horizontal = 20.dp)
            ) {
                HomeScreenUi(
                    state = HomeScreenState(),
                    scrollEvent = emptyFlow(),
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
                    .background(Color.Red)
                    .padding(horizontal = 20.dp)
            ) {
                HomeScreenUi(
                    state = HomeScreenState(),
                    scrollEvent = emptyFlow(),
                    proceedIntent = {}
                )
            }
        }
    }
}