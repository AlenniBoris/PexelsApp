package com.example.pexapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexapp.entity.Collections
import com.example.pexapp.entity.Photo
import com.example.pexapp.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel(){
    val photos = MutableStateFlow<List<Photo?>>(listOf())
    val featuredCollections = MutableStateFlow<List<Collections?>>(listOf())

    val errorState = MutableStateFlow<Boolean>(false)

    val queryText = MutableStateFlow<String>("")
    val selectedFeaturedCollectionId = MutableStateFlow<String>("")


    init {
        viewModelScope.launch {
            getCuratedPhotos()
            getFeaturedCollection()
        }
    }

    suspend fun getFeaturedCollection(){
        val featuredList = photoRepository.getFeaturedCollectionsList(per_page = 7, page = 1)
        featuredCollections.emit(
            featuredList
        )
    }

    suspend fun getCuratedPhotos(){
        val curatedList = photoRepository.getCuratedPhotosList(page = 30, per_page = 1)
        errorState.value = curatedList.isEmpty()
        photos.emit(
            curatedList
        )
    }

    suspend fun getQueryPhotos(query: String){
        val queryPhotos = photoRepository.getSearchedPhotosList(query = query, page = 30, per_page = 1)
        errorState.value = queryPhotos.isEmpty()
        photos.emit(
            queryPhotos
        )
    }

    fun queryTextChanged(newQuery: String){
        queryText.value = newQuery
        selectedFeaturedCollectionId.value = ""
    }

    fun selectedFeaturedCollectionIdChanged(id: String){
        selectedFeaturedCollectionId.value = id
    }
}