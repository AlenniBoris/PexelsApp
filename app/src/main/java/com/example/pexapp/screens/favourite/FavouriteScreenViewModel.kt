package com.example.pexapp.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexapp.data.repository.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteScreenViewModel @Inject constructor(
    private val favouriteRepository: FavouritesRepository
) : ViewModel(){

    val screenState = MutableStateFlow(FavouriteScreenState())

    init {
        viewModelScope.launch {
            getFavouritePhotosInit()
        }
    }

    suspend fun getFavouritePhotosInit(){
        val favouriteList = favouriteRepository.getAllFavourites()

        viewModelScope.launch {
            screenState.update { state ->
                state.copy(
                    favouritePhotos = favouriteList,
                    isNoFavourite = favouriteList.isEmpty()
                )
            }
        }
    }

}