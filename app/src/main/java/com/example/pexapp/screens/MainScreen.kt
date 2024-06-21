package com.example.pexapp.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pexapp.entity.PhotoEntity
import com.example.pexapp.navigation.Route
import com.example.pexapp.navigation.Screen
import com.example.pexapp.navigation.screensToNavigate
import com.example.pexapp.viewmodel.MainScreenViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
){

    val context = LocalContext.current

    val curatedPhotosList by mainScreenViewModel.curatedPhotos.collectAsStateWithLifecycle()
    val featuredCollectionsList by mainScreenViewModel.featuredCollections.collectAsStateWithLifecycle()

    Log.d("curated", curatedPhotosList.toString())
    Log.d("featured", featuredCollectionsList.toString())


    Column {
        //Photos
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 62.dp, bottom = 24.dp)
        ) {
            items(curatedPhotosList){
                PhotoCard(it.src.medium, it.id, navController)
            }
        }


        Text(text = "Main")
        Button(onClick = {
            navController.navigate(Screen.Details.route+"5")
        }) {
            Text(text = "details")
        }

    }

}

@Composable
fun PhotoCard(
    photoUrl: String,
    id: Int,
    navController: NavHostController
){
    AsyncImage(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                navController.navigate(Screen.Details.route+id.toString())
                Log.d("id", id.toString())
            },
        model = photoUrl,
        contentDescription = "Image",
        contentScale = ContentScale.FillWidth
    )
}