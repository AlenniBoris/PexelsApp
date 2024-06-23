package com.example.pexapp.screens

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pexapp.R
import com.example.pexapp.navigation.Screen
import com.example.pexapp.ui.theme.White
import com.example.pexapp.utils.AppLoadingProgressBar
import com.example.pexapp.utils.AppSearchBar
import com.example.pexapp.viewmodel.MainScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



@Composable
fun MainScreen(
    navController: NavHostController,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
){

    val context = LocalContext.current
    val window = (context as Activity).window
    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
    val scope = rememberCoroutineScope()

    val photosList by mainScreenViewModel.photos.collectAsStateWithLifecycle()
    val featuredCollectionsList by mainScreenViewModel.featuredCollections.collectAsStateWithLifecycle()

    val errorState by mainScreenViewModel.errorState.collectAsStateWithLifecycle()

    val queryText by mainScreenViewModel.queryText.collectAsStateWithLifecycle()
    val selectedFeaturedCollectionId by mainScreenViewModel.selectedFeaturedCollectionId.collectAsStateWithLifecycle()


    val internetError = hasInternetConnection(context)

    if (!internetError){
        Toast.makeText(context, "Please, check your Interner connection", Toast.LENGTH_SHORT).show()
    }

    Log.d("curated", queryText.toString())
    Log.d("featured", featuredCollectionsList.toString())

    var active by remember { mutableStateOf(false) }
    val history = remember { mutableSetOf<String>() }

    Column {

        AppSearchBar(
            active = active,
            query = queryText,
            history = history,
            onQueryChanged = {
                mainScreenViewModel.queryTextChanged(it)
            },
            onSearch = {
                history.add(it)
                active = false
                scope.launch {
                    if (it.isNotBlank()){
                        mainScreenViewModel.getQueryPhotos(it)
                    } else {
                        mainScreenViewModel.getCuratedPhotos()
                    }
                }
            },
            onActiveChanged = {
                active = it
            }
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(11.dp),

            ) {
            items(featuredCollectionsList) { it ->
                FeaturedItem(
                    title = it?.title,
                    selected = it?.id == selectedFeaturedCollectionId
                ) {
                    changeSearch(
                        scope = scope,
                        mainScreenViewModel = mainScreenViewModel,
                        title = it?.title,
                        id = it?.id
                    )
                }
            }

        }

        AppLoadingProgressBar(isLoading = photosList.isEmpty() && !errorState)


        if (photosList.isNotEmpty() && !errorState){
            //Photos
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 62.dp)
            ) {
                items(photosList){photo ->
                    PhotoCard(
                        id = photo?.id,
                        url = photo?.src?.medium,
                        author = photo?.photographer,
                        navController = navController
                    )
                }
            }
        }
        if (!internetError){
            EmptyScreen(
                onExploreClicked = {
                    scope.launch {
                        if (queryText.isNotBlank()){
                            mainScreenViewModel.getQueryPhotos(queryText)
                        }else{
                            mainScreenViewModel.getCuratedPhotos()
                        }
                    }
                },
                text = "No results found",
                isMainScreen = true,
                internetError = false
            )
        } else {
            EmptyScreen(
                onExploreClicked = {
                    scope.launch {
                        if (queryText.isNotBlank()){
                            mainScreenViewModel.getQueryPhotos(queryText)
                        }else{
                            mainScreenViewModel.getCuratedPhotos()
                        }
                    }
                },
                text = "No internet",
                isMainScreen = true,
                internetError = true,
                btnText = "Try Again"
            )
        }
    }

}

fun hasInternetConnection(context: Context): Boolean{
    var res: Boolean = true
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run{
        res = when{
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            else -> false
        }
    }
    return res
}

fun changeSearch(scope: CoroutineScope, mainScreenViewModel: MainScreenViewModel, title: String?, id: String?){
    mainScreenViewModel.queryTextChanged(title!!)
    mainScreenViewModel.selectedFeaturedCollectionIdChanged(id!!)
    scope.launch {
        mainScreenViewModel.getQueryPhotos(title)
    }
}

@Composable
fun FeaturedItem(
    title: String?,
    selected: Boolean,
    onClick: () -> Unit
) {
    val background =
        if (selected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary

    val textColor = if (selected) Color.White else MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(background)
            .clickable { onClick() },
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            text = title!!,
            color = textColor,
            fontSize = 14.sp
        )
    }
}

@Composable
fun PhotoCard(
    id: Int?,
    url: String?,
    author: String?,
    navController: NavHostController
){
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.BottomCenter
    ){
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    navController.navigate(Screen.Details.route + id.toString())
                },
            model = url,
            contentDescription = "Image",
            contentScale = ContentScale.FillWidth,
            placeholder =
            if (isSystemInDarkTheme()) painterResource(id = R.drawable.ic_placeholder_dark)
            else painterResource(id = R.drawable.ic_placeholder_light)
        )
    }
}