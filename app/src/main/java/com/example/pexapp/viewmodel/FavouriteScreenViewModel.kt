package com.example.pexapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexapp.entity.Photo
import com.example.pexapp.repository.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteScreenViewModel @Inject constructor(
    private val favouriteRepository: FavouritesRepository
) : ViewModel(){

    val favouritePhotos = MutableStateFlow<List<Photo>>(listOf())
    val isNoFavourite = MutableStateFlow<Boolean>(false)

    init {
        viewModelScope.launch {
            getFavouritePhotosInit()
        }
    }

    suspend fun getFavouritePhotosInit(){
        val favouriteList = favouriteRepository.getAllFavourites()
        favouritePhotos.emit(favouriteList)
        isNoFavourite.value = favouriteList.isEmpty()
    }

}