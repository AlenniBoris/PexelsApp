package com.example.pexapp.screens.main.views

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.pexapp.R
import com.example.pexapp.screens.EmptyScreen
import com.example.pexapp.screens.main.MainScreenViewModel
import com.example.pexapp.uikit.views.AppLoadingProgressBar
import com.example.pexapp.uikit.views.AppSearchBar
import com.example.pexapp.utils.ExtraFunctions.changeSearch
import com.example.pexapp.utils.ExtraFunctions.hasInternetConnection
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
){

    val context = LocalContext.current
    val window = (context as Activity).window
    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
    val scope = rememberCoroutineScope()

    val hasInternet = hasInternetConnection(context)

    if (!hasInternet){
        Toast.makeText(context, stringResource(id = R.string.check_internet_string), Toast.LENGTH_SHORT).show()
    }

    val state by mainScreenViewModel.screenState.collectAsStateWithLifecycle()

    val featuredItemsLazyState = rememberLazyListState()

    LaunchedEffect(key1 = mainScreenViewModel) {
        mainScreenViewModel.scrollEvent
            .onEach { featuredItemsLazyState.animateScrollToItem(0) }
            .launchIn(this)
    }

    Column {

        AppSearchBar(
            active = state.isActive,
            query = state.queryText,
            history = state.history,
            onQueryChanged = { mainScreenViewModel.searchPhoto(it) },
            onSearch = {
                mainScreenViewModel.forceSearchPhoto(it)
            },
            onActiveChanged = {
                mainScreenViewModel.changeIsActive(it)
            }
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(11.dp),
            state = featuredItemsLazyState,
        ) {
            items(state.featuredCollections) { item ->
                FeaturedItem(
                    modifier = Modifier.animateItemPlacement(),
                    title = item.title,
                    selected =
                        (item.id == state.selectedFeaturedCollectionId)
                                || (state.queryText == item.title)
                ) {
                    changeSearch(
                        scope = scope,
                        mainScreenViewModel = mainScreenViewModel,
                        title = item.title,
                        id = item.id
                    )
                }
            }

        }

        AppLoadingProgressBar(isLoading = state.photos.isEmpty() && !state.errorState)


        if (state.photos.isNotEmpty() && !state.errorState){
            //Photos
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp)
            ) {
                items(state.photos){photo ->
                    PhotoCard(
                        id = photo.id,
                        url = photo.src.medium,
                        author = photo.photographer,
                        navController = navController
                    )
                }
            }
        }
        if (state.photos.isEmpty()){
            if (!hasInternet){
                EmptyScreen(
                    onExploreClicked = {
                        mainScreenViewModel.searchPhoto(state.queryText)
                    },
                    text = stringResource(id = R.string.no_internet_string),
                    isMainScreen = true,
                    hasInternet = hasInternet,
                    btnText = stringResource(id = R.string.try_again_string)
                )
            }else{
                EmptyScreen(
                    onExploreClicked = {
                        mainScreenViewModel.searchPhoto(state.queryText)
                    },
                    text = stringResource(id = R.string.no_results_string),
                    isMainScreen = true,
                    hasInternet = hasInternet
                )
            }
        }
    }

}





