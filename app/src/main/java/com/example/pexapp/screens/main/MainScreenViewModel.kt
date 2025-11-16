package com.example.pexapp.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.example.pexapp.data.repository.WebDataRepositoryImpl
import com.example.pexapp.data.source.api.PhotoApiService
import com.example.pexapp.domain.repository.IWebDataRepository
import com.example.pexapp.domain.usecase.implementation.web.GetCuratedPhotosUseCaseImpl
import com.example.pexapp.domain.usecase.implementation.web.GetFeaturedCollectionsUseCaseImpl
import com.example.pexapp.domain.usecase.implementation.web.GetPhotoByIdUseCaseImpl
import com.example.pexapp.domain.usecase.implementation.web.GetPhotosByQueryUseCaseImpl
import com.example.pexapp.domain.usecase.logic.web.IGetCuratedPhotosUseCase
import com.example.pexapp.domain.usecase.logic.web.IGetFeaturedCollectionsUseCase
import com.example.pexapp.domain.usecase.logic.web.IGetPhotoByIdUseCase
import com.example.pexapp.domain.usecase.logic.web.IGetPhotosByQueryUseCase
import com.example.pexapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getCuratedPhotosUseCase: IGetCuratedPhotosUseCase,
    private val getFeaturedCollectionsUseCase: IGetFeaturedCollectionsUseCase,
    private val getPhotoByIdUseCase: IGetPhotoByIdUseCase,
    private val getPhotosByQueryUseCase: IGetPhotosByQueryUseCase
) : ViewModel() {

    fun aa() {
        viewModelScope.launch {
            try {
                val res = getPhotosByQueryUseCase.invoke(query = "sun")
                Log.e("!!!!", res.result?.size.toString())
            } catch (e: Exception) {
                Log.e("!!!!", e.stackTraceToString())
            }
        }
    }

    private val searchFlow = MutableSharedFlow<String>()
    val screenState = MutableStateFlow(MainScreenState())

    private val _scrollEvent = Channel<Unit>()
    val scrollEvent: Flow<Unit> = _scrollEvent.receiveAsFlow().flowOn(Dispatchers.Main.immediate)

//    init {
//        viewModelScope.launch {
//            getCuratedPhotos()
//            getFeaturedCollection()
//        }
//        searchFlow
//            .debounce(2500L)
//            .onEach { query -> searchPhotoInternal(query) }
//            .launchIn(viewModelScope)
//    }
//
//    fun searchPhoto(query: String) {
//        viewModelScope.launch {
//            screenState.update { state ->
//                state.copy(
//                    queryText = query,
//                    selectedFeaturedCollectionId = "",
//                    featuredCollections = state.initialFeaturedCollections
//                )
//            }
//
//            searchFlow.emit(query)
//        }
//    }
//
//    fun forceSearchPhoto(query: String) {
//        viewModelScope.launch {
//            screenState.update { state ->
//                state.copy(
//                    queryText = query,
//                    selectedFeaturedCollectionId = "",
//                    featuredCollections = state.initialFeaturedCollections
//                )
//            }
//
//            searchPhotoInternal(query)
//
//        }
//        viewModelScope.launch {
//            searchPhotoInternal(query)
//        }
//    }
//
//    fun changeIsActive(isActive: Boolean) {
//
//        screenState.update { state ->
//            state.copy(
//                isActive = isActive
//            )
//        }
//    }
//
//    private suspend fun searchPhotoInternal(query: String) {
//        screenState.update { state ->
//            state.copy(
//                isActive = false,
//                history = if (query != "") state.history + query else state.history
//            )
//        }
//        if (query.isNotEmpty()) {
//            getQueryPhotos(query)
//        } else {
//            getCuratedPhotos()
//        }
//    }
//
//    suspend fun getFeaturedCollection() {
//        val featuredList = photoRepository.getFeaturedCollectionsList(
//            perPage = Constants.NUMBER_FEATURED,
//            page = Constants.PER_PAGE
//        )
//        screenState.update { state ->
//            state.copy(
//                featuredCollections = featuredList,
//                initialFeaturedCollections = featuredList
//            )
//        }
//    }
//
//    suspend fun getCuratedPhotos() {
//        val curatedList = photoRepository.getCuratedPhotosList(
//            page = Constants.NUMBER_CURATED,
//            perPage = Constants.PER_PAGE
//        )
//        screenState.update { state ->
//            state.copy(
//                errorState = curatedList.isEmpty(),
//                photos = curatedList
//            )
//        }
//    }
//
//    suspend fun getQueryPhotos(query: String) {
//        val queryPhotos = photoRepository.getSearchedPhotosList(
//            query = query,
//            page = Constants.NUMBER_CURATED,
//            perPage = Constants.PER_PAGE
//        )
//        screenState.update { state ->
//            state.copy(
//                errorState = queryPhotos.isEmpty(),
//                photos = queryPhotos
//            )
//        }
//    }
//
//    fun queryTextChanged(newQuery: String) {
//        screenState.update { state ->
//            state.copy(
//                queryText = newQuery,
//                selectedFeaturedCollectionId = "",
//                featuredCollections = state.initialFeaturedCollections
//            )
//        }
//    }
//
//    fun selectedFeaturedCollectionIdChanged(id: String) {
//        screenState.update { state ->
//            val currentCollections = state.initialFeaturedCollections.toMutableList()
//            val foundIndex = currentCollections.indexOfFirst { item -> item.id == id }
//
//            if (foundIndex != -1) {
//                val firstElement = currentCollections.removeAt(foundIndex)
//                currentCollections.add(0, firstElement)
//            }
//
//            state.copy(
//                selectedFeaturedCollectionId = id,
//                featuredCollections = currentCollections.toList()
//            )
//        }
//
//        viewModelScope.launch {
//            _scrollEvent.send(Unit)
//        }
//    }
}