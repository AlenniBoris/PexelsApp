package com.example.pexapp.presentation.screens.details.views
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pexapp.R
import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.domain.util.GsonUtil.fromJson
import com.example.pexapp.domain.util.GsonUtil.toJson
import com.example.pexapp.domain.util.LogPrinter
import com.example.pexapp.presentation.model.PhotoModelUi
import com.example.pexapp.presentation.model.toUiModel
import com.example.pexapp.presentation.navigation.AppScreen
import com.example.pexapp.presentation.screens.details.DetailsScreenState
import com.example.pexapp.presentation.screens.details.DetailsScreenValues
import com.example.pexapp.presentation.screens.details.DetailsScreenViewModel
import com.example.pexapp.presentation.screens.details.IDetailsScreenEvent
import com.example.pexapp.presentation.screens.details.IDetailsScreenIntent
import com.example.pexapp.presentation.uikit.theme.PexAppTheme
import com.example.pexapp.presentation.uikit.theme.appColor
import com.example.pexapp.presentation.uikit.theme.appContentPadding
import com.example.pexapp.presentation.uikit.theme.appPhotoSectionSpacing
import com.example.pexapp.presentation.uikit.theme.appRoundedShape
import com.example.pexapp.presentation.uikit.theme.appTextSize
import com.example.pexapp.presentation.uikit.theme.appTextStyle
import com.example.pexapp.presentation.uikit.theme.baseTextColor
import com.example.pexapp.presentation.uikit.theme.clickableElementBackground
import com.example.pexapp.presentation.uikit.theme.detailsButtonsInnerPadding
import com.example.pexapp.presentation.uikit.theme.detailsDownloadTextPadding
import com.example.pexapp.presentation.uikit.theme.detailsLikedOuterPadding
import com.example.pexapp.presentation.uikit.theme.detailsScreenButtonsVerticalPadding
import com.example.pexapp.presentation.uikit.theme.detailsScreenLoadingBorderWidth
import com.example.pexapp.presentation.uikit.theme.detailsScreenPicMinSize
import com.example.pexapp.presentation.uikit.theme.detailsScreenSectionTopPadding
import com.example.pexapp.presentation.uikit.theme.detailsScreenThemedPhotosLoadingPadding
import com.example.pexapp.presentation.uikit.theme.supportButtonsBackgroundColor
import com.example.pexapp.presentation.uikit.theme.supportButtonsTextColor
import com.example.pexapp.presentation.uikit.theme.topBarInnerPadding
import com.example.pexapp.presentation.uikit.theme.topBarOuterPadding
import com.example.pexapp.presentation.uikit.utils.CommonValues
import com.example.pexapp.presentation.uikit.utils.PermissionType
import com.example.pexapp.presentation.uikit.utils.animatedBorder
import com.example.pexapp.presentation.uikit.utils.launchForPermission
import com.example.pexapp.presentation.uikit.utils.toPermission
import com.example.pexapp.presentation.uikit.views.AppExceptionScreen
import com.example.pexapp.presentation.uikit.views.AppPermissionRationaleDialog
import com.example.pexapp.presentation.uikit.views.AppPhotoCard
import com.example.pexapp.presentation.uikit.views.AppProgressBar
import com.example.pexapp.presentation.uikit.views.AppRefreshIndicator
import com.example.pexapp.presentation.uikit.views.AppTopBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun DetailsScreen(
    photoJson: String?,
    navController: NavHostController
) {

    val context = LocalContext.current

    val viewModel: DetailsScreenViewModel =
        hiltViewModel<DetailsScreenViewModel, DetailsScreenViewModel.DetailsScreenViewModelFactory> { factory ->
            factory.create(
                photo = photoJson?.fromJson<PhotoModelUi>()
            )
        }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val event by remember { mutableStateOf(viewModel.event) }
    var toast by remember {
        mutableStateOf(Toast.makeText(context, "", Toast.LENGTH_SHORT))
    }
    val downloadEvent by remember { mutableStateOf(viewModel.downloadEvent) }

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<IDetailsScreenEvent.NavigateBack>().collect {
                navController.popBackStack()
            }
        }
        launch {
            event.filterIsInstance<IDetailsScreenEvent.ShowToast>().collect { coming ->
                toast?.cancel()
                toast = Toast.makeText(
                    context,
                    context.getString(coming.messageId),
                    Toast.LENGTH_SHORT
                )
                toast?.show()
            }
        }
        launch {
            event.filterIsInstance<IDetailsScreenEvent.OpenPhoto>().collect { coming ->
                val encodedJson = Uri.encode(coming.photo.toJson())
                navController.navigate(AppScreen.Details.route + encodedJson)
            }
        }
        launch {
            event.filterIsInstance<IDetailsScreenEvent.OpenSettings>().collect {
                val openingIntent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                context.startActivity(openingIntent)
            }
        }
    }

    DetailsScreenUi(
        state = state,
        downloadEvent = downloadEvent,
        proceedIntent = proceedIntent
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun DetailsScreenUi(
    state: DetailsScreenState,
    downloadEvent: Flow<Unit>,
    proceedIntent: (IDetailsScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        AppTopBar(
            modifier = Modifier
                .padding(topBarOuterPadding)
                .fillMaxWidth()
                .padding(topBarInnerPadding),
            trailingIcon = painterResource(R.drawable.navigate_back),
            onTrailingClicked = {
                proceedIntent(
                    IDetailsScreenIntent.NavigateBack
                )
            },
            text = when (state) {
                is DetailsScreenState.Initial -> ""
                is DetailsScreenState.DetailsWithInternet, is DetailsScreenState.DetailsWithoutInternet ->
                    state.photo?.domainModel?.photographer ?: state.photo?.simpleModel?.photographer

                is DetailsScreenState.Error -> null
            } ?: stringResource(R.string.nan_text)
        )

        when (state) {
            is DetailsScreenState.Initial -> {}
            is DetailsScreenState.Error -> {
                AppExceptionScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(detailsScreenSectionTopPadding)
                        .padding(appContentPadding)
                        .verticalScroll(rememberScrollState()),
                    exception = state.exception,
                    onTryAgain = {
                        proceedIntent(
                            IDetailsScreenIntent.RetryPhotoLoad
                        )
                    }
                )
            }

            is DetailsScreenState.DetailsWithoutInternet -> {
                AppExceptionScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(detailsScreenSectionTopPadding)
                        .padding(appContentPadding)
                        .verticalScroll(rememberScrollState()),
                    exception = CommonExceptionModelDomain.InternetException.toUiModel(),
                    onTryAgain = {
                        proceedIntent(
                            IDetailsScreenIntent.RetryPhotoLoad
                        )
                    }
                )
            }

            else -> {

                val refreshState = rememberPullToRefreshState()
                PullToRefreshBox(
                    modifier = Modifier.fillMaxSize(),
                    isRefreshing = state.isRefreshing,
                    onRefresh = {
                        proceedIntent(
                            IDetailsScreenIntent.RefreshData
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
                    (state as? DetailsScreenState.DetailsWithInternet)?.let {
                        DetailsWithInternetUi(
                            modifier = Modifier
                                .padding(detailsScreenSectionTopPadding)
                                .fillMaxSize()
                                .padding(appContentPadding),
                            state = state,
                            downloadEvent = downloadEvent,
                            proceedIntent = proceedIntent
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun DetailsWithInternetUi(
    modifier: Modifier = Modifier,
    state: DetailsScreenState.DetailsWithInternet,
    downloadEvent: Flow<Unit>,
    proceedIntent: (IDetailsScreenIntent) -> Unit
) {

    val listState = rememberLazyStaggeredGridState()
    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItem >= layoutInfo.totalItemsCount - DetailsScreenValues.LOADING_BOUNDARY_LIMIT
        }
    }
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore &&
            state.isLoadingMorePossible &&
            !state.isThemedPhotosLoading
        ) {
            proceedIntent(IDetailsScreenIntent.LoadMoreThemedPhotos)
        }
    }

    LazyVerticalStaggeredGrid(
        modifier = modifier,
        state = listState,
        columns = StaggeredGridCells.Fixed(CommonValues.NUMBER_OF_COLUMNS),
        verticalItemSpacing = appPhotoSectionSpacing,
        horizontalArrangement = Arrangement.spacedBy(appPhotoSectionSpacing)
    ) {

        item(span = StaggeredGridItemSpan.FullLine) {
            val pictureUrl = remember(state.photo) {
                if (state.photo.domainModel != null) state.photo.domainModel.src.original
                else state.photo.simpleModel?.photoPictureOriginalSizeUrl
            }

            AsyncImage(
                modifier = Modifier
                    .clip(appRoundedShape)
                    .sizeIn(
                        minWidth = detailsScreenPicMinSize,
                        minHeight = detailsScreenPicMinSize
                    ),
                model = pictureUrl,
                contentDescription = stringResource(id = R.string.image_description_string),
                contentScale = ContentScale.FillWidth,
                placeholder =
                    if (isSystemInDarkTheme()) painterResource(id = R.drawable.ic_placeholder_dark)
                    else painterResource(id = R.drawable.ic_placeholder_light)
            )
        }

        item(span = StaggeredGridItemSpan.FullLine) {
            DetailsActionButtonsRow(
                modifier = Modifier
                    .padding(detailsScreenButtonsVerticalPadding)
                    .fillMaxWidth(),
                isLiked = state.isLiked,
                isLoadingToMemory = state.isLoadingToMemory,
                downloadEvent = downloadEvent,
                isPermissionDialogVisible = state.isPermissionDialogVisible,
                requestedPermission = state.requestedPermission,
                proceedIntent = proceedIntent
            )
        }

        items(state.themedPhotos) { photo ->
            AppPhotoCard(
                modifier = Modifier
                    .clip(appRoundedShape)
                    .clickable {
                        proceedIntent(
                            IDetailsScreenIntent.OpenPhotoDetails(photo)
                        )
                    },
                photo = photo,
            )
        }

        if (state.isThemedPhotosLoading) {
            item(span = StaggeredGridItemSpan.FullLine) {
                AppProgressBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(detailsScreenThemedPhotosLoadingPadding)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun DetailsActionButtonsRow(
    modifier: Modifier = Modifier,
    isLiked: Boolean,
    isLoadingToMemory: Boolean,
    downloadEvent: Flow<Unit>,
    isPermissionDialogVisible: Boolean,
    requestedPermission: PermissionType?,
    proceedIntent: (IDetailsScreenIntent) -> Unit
) {

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        LogPrinter.printLog("!!!!", "permission is granted: $isGranted")
    }
    LaunchedEffect(downloadEvent) {
        downloadEvent
            .onEach {
                launchForPermission(
                    permission = PermissionType.PERMISSION_WRITE_STORAGE,
                    context = context,
                    onPermissionGrantedAction = {
                        proceedIntent(
                            IDetailsScreenIntent.StartDownloading(
                                resolver = context.contentResolver
                            )
                        )
                    },
                    onPermissionNotGrantedAction = {},
                    onShowRationale = { permission ->
                        proceedIntent(
                            IDetailsScreenIntent.UpdateRequestedPermissionAndShowDialog(
                                newRequestedPermission = permission
                            )
                        )
                    },
                    onLaunchAgain = { permission ->
                        permissionLauncher.launch(permission.toPermission())
                    }
                )
            }
            .launchIn(this)
    }
    if (isPermissionDialogVisible) {
        requestedPermission?.let {
            AppPermissionRationaleDialog(
                permissionType = it,
                onDismiss = {
                    proceedIntent(
                        IDetailsScreenIntent.HidePermissionDialog
                    )
                },
                onOpenSettings = {
                    proceedIntent(
                        IDetailsScreenIntent.OpenSettingsAndHidePermissionDialog
                    )
                }
            )
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Button(
            modifier = Modifier
                .animatedBorder(
                    borderColors = listOf(clickableElementBackground, baseTextColor),
                    backgroundColor = clickableElementBackground,
                    shape = appRoundedShape,
                    isBorderVisible = isLoadingToMemory,
                    borderWidth = detailsScreenLoadingBorderWidth,
                ),
            onClick = {
                proceedIntent(IDetailsScreenIntent.DownloadPhoto)
            },
            enabled = !isLoadingToMemory,
            contentPadding = PaddingValues.Zero
        ) {

            Row(
                modifier = Modifier.background(clickableElementBackground),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .clip(appRoundedShape)
                        .background(supportButtonsBackgroundColor)
                        .padding(detailsButtonsInnerPadding),
                    painter = painterResource(R.drawable.download_icon),
                    tint = supportButtonsTextColor,
                    contentDescription = stringResource(R.string.picture_description)
                )

                Text(
                    modifier = Modifier.padding(detailsDownloadTextPadding),
                    text = stringResource(R.string.download_text),
                    style = appTextStyle.copy(
                        color = baseTextColor,
                        fontSize = appTextSize
                    )
                )
            }

        }

        Icon(
            modifier = Modifier
                .padding(detailsLikedOuterPadding)
                .clip(CircleShape)
                .background(clickableElementBackground)
                .padding(detailsButtonsInnerPadding)
                .clickable {
                    proceedIntent(
                        IDetailsScreenIntent.ProceedLikedAction
                    )
                },
            painter = painterResource(
                if (isLiked) R.drawable.icon_favourites_active
                else R.drawable.icon_favourites_not_active
            ),
            tint = baseTextColor,
            contentDescription = stringResource(R.string.picture_description)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
private fun LightTheme() {
    PexAppTheme(
        darkTheme = false
    ) {
        Surface {
            DetailsScreenUi(
                state = DetailsScreenState.Initial,
                downloadEvent = emptyFlow(),
                proceedIntent = {}
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
private fun DarkTheme() {
    PexAppTheme(
        darkTheme = true
    ) {
        Surface {
            DetailsScreenUi(
                state = DetailsScreenState.Initial,
                downloadEvent = emptyFlow(),
                proceedIntent = {}
            )
        }
    }
}