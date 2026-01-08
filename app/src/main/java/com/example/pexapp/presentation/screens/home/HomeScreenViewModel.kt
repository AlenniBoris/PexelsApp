package com.example.pexapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexapp.domain.model.CustomResultModelDomain
import com.example.pexapp.domain.usecase.logic.web.IGetCuratedPhotosUseCase
import com.example.pexapp.domain.usecase.logic.web.IGetFeaturedCollectionsUseCase
import com.example.pexapp.domain.usecase.logic.web.IGetPhotosByQueryUseCase
import com.example.pexapp.domain.util.LogPrinter
import com.example.pexapp.domain.util.SingleFlowEvent
import com.example.pexapp.presentation.mappers.toUiString
import com.example.pexapp.presentation.model.FeaturedCollectionModelUi
import com.example.pexapp.presentation.model.PhotoModelUi
import com.example.pexapp.presentation.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
class HomeScreenViewModel @Inject constructor(
    private val getCuratedPhotosUseCase: IGetCuratedPhotosUseCase,
    private val getFeaturedCollectionsUseCase: IGetFeaturedCollectionsUseCase,
    private val getPhotosByQueryUseCase: IGetPhotosByQueryUseCase
) : ViewModel() {

    private val _searchFlow = MutableSharedFlow<String>()

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<IHomeScreenEvent>(viewModelScope)
    val event = _event.flow

    private val _scrollEvent = Channel<Unit>()
    val scrollEvent = _scrollEvent.receiveAsFlow().flowOn(Dispatchers.Main.immediate)

    private var _photosJob: Job? = null
    private var _featuredCollectionsJob: Job? = null

    init {
        _searchFlow
            .debounce(HomeScreenValues.USER_FRIENDLY_TIME)
            .onEach { query ->
                if (_state.value.suppressDebounce) {
                    _state.update { it.copy(suppressDebounce = false) }
                    return@onEach
                }
                loadNewPhotosInternal()
            }
            .launchIn(viewModelScope)
    }

    init {
        getCuratedPhotos()
        loadFeaturedCollections()
    }

    private fun loadFeaturedCollections() {
        _featuredCollectionsJob?.cancel()
        _featuredCollectionsJob = viewModelScope.launch {
            _state.update { it.copy(isFeaturedLoading = true) }
            when (
                val res = getFeaturedCollectionsUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _state.update {
                        it.copy(
                            featuredCollections = res.result.map { it.toUiModel() },
                            initialFeaturedCollections = res.result.map { it.toUiModel() }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IHomeScreenEvent.ShowMessage(
                            res.exception.toUiString()
                        )
                    )
                }
            }
            _state.update { it.copy(isFeaturedLoading = false) }
        }
    }

    private fun getCuratedPhotos() {
        _photosJob?.cancel()
        _photosJob = viewModelScope.launch {
            _state.update { it.copy(isPhotosLoading = true) }
            getCuratedPhotosInternal()
            _state.update { it.copy(isPhotosLoading = false) }
        }
    }

    private suspend fun getCuratedPhotosInternal() {
        when (
            val res = getCuratedPhotosUseCase.invoke()
        ) {
            is CustomResultModelDomain.Success -> {
                _state.update {
                    it.copy(
                        photos = res.result.map { it.toUiModel() }
                    )
                }
            }

            is CustomResultModelDomain.Error -> {
                _event.emit(
                    IHomeScreenEvent.ShowMessage(
                        res.exception.toUiString()
                    )
                )
                _state.update {
                    it.copy(
                        exception = res.exception.toUiModel()
                    )
                }
            }
        }
    }

    fun proceedIntent(intent: IHomeScreenIntent) {
        when (intent) {
            is IHomeScreenIntent.RetryPhotosRequest -> retryPhotosRequest()
            is IHomeScreenIntent.RefreshData -> refreshData()
            is IHomeScreenIntent.OpenPhotoDetails -> openPhotoDetails(intent.photo)
            is IHomeScreenIntent.LoadMoreData -> loadMoreData()
            is IHomeScreenIntent.SelectFeaturedCollection -> selectFeaturedCollection(intent.collection)
            is IHomeScreenIntent.RetryFeaturedCollectionsRequest -> retryFeaturedCollectionsRequest()
            is IHomeScreenIntent.ChangeQuery -> changeQuery(intent.newValue)
            is IHomeScreenIntent.ForceSearchPhotos -> forceSearchPhotos()
            is IHomeScreenIntent.ChangeHistoryVisibility -> changeHistoryVisibility(intent.isVisible)
            is IHomeScreenIntent.ClearQuery -> clearQuery()
            is IHomeScreenIntent.DeleteHistoryQuery -> deleteHistoryQuery(intent.query)
        }
    }

    private fun deleteHistoryQuery(query: String) {
        _state.update { state ->
            state.copy(
                history = state.history - query
            )
        }
    }

    private fun clearQuery() {
        changeQuery("")
    }

    private fun changeHistoryVisibility(isVisible: Boolean) {
        _state.update { it.copy(isHistoryVisible = isVisible) }
    }

    private fun forceSearchPhotos() {
        _state.update { it.copy(suppressDebounce = true) }
        loadNewPhotosInternal()
    }

    private fun changeQuery(newValue: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    query = newValue
                )
            }
            val newSelectedFeatured =
                _state.value.featuredCollections.firstOrNull { it.text == newValue }
            newSelectedFeatured?.let { collection ->
                _state.update {
                    val currList = it.initialFeaturedCollections.toMutableList()
                    val foundIndex = currList.indexOfFirst { it == collection }
                    if (foundIndex != -1) {
                        val elem = currList.removeAt(foundIndex)
                        currList.add(0, elem)
                    }

                    it.copy(
                        selectedFeaturedCollection = if (it.selectedFeaturedCollection == collection) null else collection,
                        featuredCollections = if (it.selectedFeaturedCollection == collection) it.initialFeaturedCollections else currList.toList()
                    )
                }

                viewModelScope.launch { _scrollEvent.send(Unit) }
            } ?: _state.update {
                it.copy(
                    selectedFeaturedCollection = null,
                    featuredCollections = it.initialFeaturedCollections
                )
            }

            _searchFlow.emit(newValue)
        }
    }

    private fun loadNewPhotosInternal() {

        _state.update { state ->
            state.copy(
                isHistoryVisible = false,
                history = if (state.workingQuery.isEmpty()) state.history else state.history + state.workingQuery
            )
        }

        if (_state.value.workingQuery.isNotEmpty()) getPhotosByQuery()
        else getCuratedPhotos()
    }

    private fun retryFeaturedCollectionsRequest() {
        loadFeaturedCollections()
    }

    private fun selectFeaturedCollection(collection: FeaturedCollectionModelUi) {
        _state.update {
            val currList = it.initialFeaturedCollections.toMutableList()
            val foundIndex = currList.indexOfFirst { it == collection }
            if (foundIndex != -1) {
                val elem = currList.removeAt(foundIndex)
                currList.add(0, elem)
            }

            it.copy(
                selectedFeaturedCollection = if (it.selectedFeaturedCollection == collection) null else collection,
                featuredCollections = if (it.selectedFeaturedCollection == collection) it.initialFeaturedCollections else currList.toList(),
                query = if (it.selectedFeaturedCollection == collection) "" else collection.text,
                history = it.history + collection.text
            )
        }

        viewModelScope.launch { _scrollEvent.send(Unit) }

        makePhotoRequest()
    }

    private fun makePhotoRequest() {
        viewModelScope.launch {
            _state.update { it.copy(isPhotosLoading = true) }
            if (_state.value.workingQuery.isEmpty()) {
                getCuratedPhotosInternal()
            } else {
                getPhotosByQueryInternal()
            }
            _state.update { it.copy(isPhotosLoading = false) }
        }
    }

    private fun loadMoreData() {
        if (_state.value.isPhotosLoading || !_state.value.isLoadingMorePossible) return

        _photosJob?.cancel()
        _photosJob = viewModelScope.launch {
            _state.update { it.copy(isPhotosLoading = true) }
            when (
                val res =
                    if (_state.value.workingQuery.isEmpty())
                        getCuratedPhotosUseCase.invoke(page = _state.value.nextPage)
                    else getPhotosByQueryUseCase.invoke(
                        query = _state.value.workingQuery,
                        page = _state.value.nextPage
                    )
            ) {
                is CustomResultModelDomain.Success -> {
                    val newData = res.result.map { it.toUiModel() }
                    _state.update {
                        it.copy(
                            photos = it.photos + newData,
                            currentPage = it.currentPage + 1,
                            pageSize = newData.size,
                            isLoadingMorePossible = newData.isNotEmpty()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IHomeScreenEvent.ShowMessage(
                            res.exception.toUiString()
                        )
                    )
                    _state.update {
                        it.copy(
                            exception = res.exception.toUiModel()
                        )
                    }
                }
            }
            _state.update { it.copy(isPhotosLoading = false) }
        }
    }

    private fun openPhotoDetails(photo: PhotoModelUi) {
        _event.emit(
            IHomeScreenEvent.OpenPhoto(photo = photo)
        )
    }

    private fun refreshData() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            if (_state.value.workingQuery.isEmpty()) {
                getCuratedPhotosInternal()
            } else {
                getPhotosByQueryInternal()
            }
            _state.update { it.copy(isRefreshing = false) }
        }
    }

    fun retryPhotosRequest() {
        _state.update {
            it.copy(
                exception = null
            )
        }
        if (_state.value.workingQuery.isEmpty()) {
            getCuratedPhotos()
        } else {
            getPhotosByQuery()
        }
    }

    private fun getPhotosByQuery() {
        _photosJob?.cancel()
        _photosJob = viewModelScope.launch {
            _state.update { it.copy(isPhotosLoading = true) }
            getPhotosByQueryInternal()
            _state.update { it.copy(isPhotosLoading = false) }
        }
    }

    private suspend fun getPhotosByQueryInternal() {
        when (
            val res = getPhotosByQueryUseCase.invoke(
                query = _state.value.workingQuery,
            )
        ) {
            is CustomResultModelDomain.Success -> {
                _state.update { state ->
                    state.copy(
                        photos = res.result.map {
                            it.toUiModel()
                        }
                    )
                }
            }

            is CustomResultModelDomain.Error -> {
                _event.emit(
                    IHomeScreenEvent.ShowMessage(
                        res.exception.toUiString()
                    )
                )
                _state.update {
                    it.copy(
                        exception = res.exception.toUiModel()
                    )
                }
            }
        }
    }
}