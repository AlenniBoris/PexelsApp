package com.example.pexapp.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexapp.data.model.Photo
import com.example.pexapp.data.repository.FavouritesRepository
import com.example.pexapp.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val favouritesRepository: FavouritesRepository
) : ViewModel(){

    val screenState = MutableStateFlow(DetailsScreenState())

    private fun changeIsFavourite(isFavourite: Boolean){
        screenState.update { state -> state.copy(photoIsFavourite = isFavourite) }
    }

    private fun assignPhoto(photo: Photo?){
        screenState.update { state ->
            state.copy(currentPhoto = photo)
        }
    }

    fun getPhotoFromPexelsById(id: Int?){
        if (id != null){
            viewModelScope.launch {
                val photo = photoRepository.getPhotoById(id)
                Log.d("PexelsById", photo.toString())
                assignPhoto(photo)
                countPhotosById(id)
            }
        }
    }

    fun getPhotoFromFavouritesDatabaseById(id: Int?){
        if (id != null){
            viewModelScope.launch {
                val favourite = favouritesRepository.getFavouriteById(id)
                Log.d("DatabaseById", favourite.toString())
                assignPhoto(favourite)
                countPhotosById(id)
            }
        }
    }

    fun actionOnFavouriteButton(photo: Photo){
        viewModelScope.launch {
            if (screenState.value.photoIsFavourite){
                removeFromFavourites(photo)
                changeIsFavourite(false)
            } else {
                addToFavourites(photo)
                changeIsFavourite(true)
            }
        }
    }

    suspend fun addToFavourites(photo: Photo) {
        viewModelScope.launch {
            favouritesRepository.addPhoto(photo)
        }

    }

    suspend fun removeFromFavourites(photo: Photo) {
        viewModelScope.launch {
            favouritesRepository.deletePhoto(photo)
        }
    }


    suspend fun countPhotosById(id: Int) {
        viewModelScope.launch {
            val count = favouritesRepository.countById(id)

            changeIsFavourite(count != 0)
        }
    }
}