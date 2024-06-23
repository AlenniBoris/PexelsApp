package com.example.pexapp.viewmodel

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexapp.entity.Photo
import com.example.pexapp.repository.FavouritesRepository
import com.example.pexapp.repository.PhotoRepository
import com.example.pexapp.utils.Downloader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val favouritesRepository: FavouritesRepository
) : ViewModel(){

    val currentPhoto = MutableStateFlow<Photo?>(null)
    val photoIsFavourite = MutableStateFlow<Boolean>(false)

    fun getPhotoFromPexelsById(id: Int?){
        if (id != null){
            viewModelScope.launch {
                val photo = photoRepository.getPhotoById(id)
                Log.d("PexelsById", photo.toString())
                currentPhoto.emit(photo)
                countPhotosById(id)
            }
        }
    }

    fun getPhotoFromFavouritesDatabaseById(id: Int?){
        if (id != null){
            viewModelScope.launch {
                val favourite = favouritesRepository.getFavouriteById(id)
                Log.d("DatabaseById", favourite.toString())
                currentPhoto.emit(favourite)
                countPhotosById(id)
            }
        }
    }

    fun actionOnFavouriteButton(photoEntity: Photo){
        viewModelScope.launch {
            if (photoIsFavourite.value){
                removeFromFavourites(photoEntity)
                photoIsFavourite.value = false
            } else {
                addToFavourites(photoEntity)
                photoIsFavourite.value = true
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
            photoIsFavourite.value = count != 0
        }
    }
}