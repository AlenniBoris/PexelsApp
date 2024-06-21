package com.example.pexapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexapp.entity.CollectionsEntity
import com.example.pexapp.entity.PhotoEntity
import com.example.pexapp.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel(){
    val curatedPhotos = MutableStateFlow<List<PhotoEntity>>(listOf())
    val featuredCollections = MutableStateFlow<List<CollectionsEntity>>(listOf())

    init {
        viewModelScope.launch {

            val curatedList = photoRepository.getCuratedPhotosList(30,1)
            curatedPhotos.emit(
                curatedList
            )

            val featuredlist = photoRepository.getFeaturedCollectionsList(1,7)
            featuredCollections.emit(
                featuredlist
            )
        }
    }
}