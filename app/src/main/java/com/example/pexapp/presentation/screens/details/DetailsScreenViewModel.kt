package com.example.pexapp.presentation.screens.details

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexapp.R
import com.example.pexapp.domain.mapper.toCommonException
import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.domain.model.CustomResultModelDomain
import com.example.pexapp.domain.model.PhotoSimpleModelDomain
import com.example.pexapp.domain.usecase.logic.database.IAddPhotoToDatabaseUseCase
import com.example.pexapp.domain.usecase.logic.database.IDeletePhotoFromDatabaseUseCase
import com.example.pexapp.domain.usecase.logic.database.IGetLikedPhotosFromDatabaseUseCase
import com.example.pexapp.domain.usecase.logic.web.IGetCuratedPhotosUseCase
import com.example.pexapp.domain.usecase.logic.web.IGetPhotosByQueryUseCase
import com.example.pexapp.domain.util.LogPrinter
import com.example.pexapp.domain.util.SingleFlowEvent
import com.example.pexapp.presentation.mappers.toUiString
import com.example.pexapp.presentation.model.PhotoModelUi
import com.example.pexapp.presentation.model.toSimpleModel
import com.example.pexapp.presentation.model.toUiModel
import com.example.pexapp.presentation.uikit.utils.ImageLoader
import com.example.pexapp.presentation.uikit.utils.ImageOperationResult
import com.example.pexapp.presentation.uikit.utils.LoaderFunctions
import com.example.pexapp.presentation.uikit.utils.NetworkMonitorUtil
import com.example.pexapp.presentation.uikit.utils.PermissionType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailsScreenViewModel.DetailsScreenViewModelFactory::class)
class DetailsScreenViewModel @AssistedInject constructor(
    @Assisted val photo: PhotoModelUi?,
    private val getLikedPhotosFromDatabase: IGetLikedPhotosFromDatabaseUseCase,
    private val deleteFromDatabaseUseCase: IDeletePhotoFromDatabaseUseCase,
    private val addToDatabaseUseCase: IAddPhotoToDatabaseUseCase,
    private val getPhotosByQueryUseCase: IGetPhotosByQueryUseCase,
    private val getCuratedPhotosUseCase: IGetCuratedPhotosUseCase,
    private val imageLoader: ImageLoader
) : ViewModel() {

    private val _state = MutableStateFlow<DetailsScreenState>(
        DetailsScreenState.Initial
    )
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<IDetailsScreenEvent>(viewModelScope)
    val event = _event.flow

    private var _themedPhotosJob: Job? = null
    private var _loadingToCacheJob: Job? = null
    private var _loadingToMemoryJob: Job? = null

    private val _downloadEvent = Channel<Unit>()
    val downloadEvent = _downloadEvent.receiveAsFlow().flowOn(Dispatchers.Main.immediate)

    init {
        dataInitiation()
    }

    init {
        viewModelScope.launch {
            getLikedPhotosFromDatabase.likedFlow
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { list ->
                    observeLikedPhotos(list)
                }
        }
    }

    fun proceedIntent(intent: IDetailsScreenIntent) {
        when (intent) {
            is IDetailsScreenIntent.DownloadPhoto -> downloadPhoto()
            is IDetailsScreenIntent.NavigateBack -> navigateBack()
            is IDetailsScreenIntent.ProceedLikedAction -> proceedLikedAction()
            is IDetailsScreenIntent.RetryPhotoLoad -> retryPhotoLoad()
            is IDetailsScreenIntent.RefreshData -> refreshData()
            is IDetailsScreenIntent.LoadMoreThemedPhotos -> loadMoreThemedPhotos()
            is IDetailsScreenIntent.OpenPhotoDetails -> openPhotoDetails(intent.photo)
            is IDetailsScreenIntent.StartDownloading -> startDownloading(intent.resolver)
            is IDetailsScreenIntent.HidePermissionDialog -> hidePermissionDialog()
            is IDetailsScreenIntent.OpenSettingsAndHidePermissionDialog -> openSettingsAndHidePermissionDialog()
            is IDetailsScreenIntent.UpdateRequestedPermissionAndShowDialog ->
                updateRequestedPermissionAndShowDialog(intent.newRequestedPermission)
        }
    }

    private fun openSettingsAndHidePermissionDialog() {
        _event.emit(
            IDetailsScreenEvent.OpenSettings
        )
        hidePermissionDialog()
    }

    private fun hidePermissionDialog() {
        _state.update { state ->
            when (state) {
                is DetailsScreenState.DetailsWithInternet -> state.copy(isPermissionDialogVisible = false)
                else -> state
            }
        }
    }

    private fun updateRequestedPermissionAndShowDialog(newPermission: PermissionType) {
        _state.update { state ->
            when (state) {
                is DetailsScreenState.DetailsWithInternet -> state.copy(
                    requestedPermission = newPermission,
                    isPermissionDialogVisible = true
                )

                else -> state
            }
        }
    }

    private fun loadMoreThemedPhotos() {
        (_state.value as? DetailsScreenState.DetailsWithInternet)?.let { internetState ->
            if (internetState.isThemedPhotosLoading || !internetState.isLoadingMorePossible) return
            loadThemedPhotos()
        }
    }

    private fun loadThemedPhotos() {
        (_state.value as? DetailsScreenState.DetailsWithInternet)?.let { internetState ->
            _themedPhotosJob?.cancel()
            _themedPhotosJob = viewModelScope.launch {
                _state.update {
                    (it as? DetailsScreenState.DetailsWithInternet)?.copy(isThemedPhotosLoading = true)
                        ?: it
                }
                loadThemedPhotosInternal(internetState)
                _state.update {
                    (it as? DetailsScreenState.DetailsWithInternet)?.copy(isThemedPhotosLoading = false)
                        ?: it
                }
            }
        }
    }

    private suspend fun loadThemedPhotosInternal(internetState: DetailsScreenState.DetailsWithInternet) {
        when (
            val res = when {
                internetState.photo.domainModel != null ->
                    getPhotosByQueryUseCase.invoke(
                        query = internetState.photo.domainModel.photographer,
                        page = internetState.currentPage
                    )

                internetState.photo.simpleModel != null ->
                    getPhotosByQueryUseCase.invoke(
                        query = internetState.photo.simpleModel.photographer,
                        page = internetState.currentPage
                    )

                else ->
                    getCuratedPhotosUseCase.invoke()
            }

        ) {
            is CustomResultModelDomain.Success -> {
                val mutablePhotos = res.result.map { it.toUiModel() }.toMutableList()
                val list = (mutablePhotos - internetState.photo).toList()
                _state.update {
                    (it as? DetailsScreenState.DetailsWithInternet)?.copy(
                        themedPhotos = internetState.themedPhotos + list,
                        currentPage = internetState.currentPage + 1,
                        pageSize = list.size,
                        isLoadingMorePossible = list.isNotEmpty()
                    ) ?: it
                }
            }

            is CustomResultModelDomain.Error -> {
                _event.emit(
                    IDetailsScreenEvent.ShowToast(
                        res.exception.toUiString()
                    )
                )
            }
        }
    }

    private fun openPhotoDetails(photo: PhotoModelUi?) {
        _event.emit(
            IDetailsScreenEvent.OpenPhoto(photo)
        )
    }

    private fun observeLikedPhotos(photos: List<PhotoSimpleModelDomain>) {
        val ids = photos.map { it.id }.toList()

        (_state.value as? DetailsScreenState.DetailsWithInternet)?.let { state ->
            _state.update {
                state.copy(
                    isLiked = ids.contains(state.photo.domainModel?.id) || ids.contains(state.photo.simpleModel?.id)
                )
            }
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            setPhotoInternal(isRefreshing = true)
            (_state.value as? DetailsScreenState.DetailsWithInternet)?.let { internetState ->
                loadThemedPhotosInternal(internetState)
            }

            changeIsRefreshing(false)
        }
    }

    private fun changeIsRefreshing(isRefreshing: Boolean) {
        _state.update { state ->
            when (state) {
                is DetailsScreenState.DetailsWithInternet -> state.copy(isRefreshing = isRefreshing)
                is DetailsScreenState.DetailsWithoutInternet -> state.copy(isRefreshing = isRefreshing)
                else -> state
            }
        }
    }

    private fun changeIsLoadingToMemory(isLoading: Boolean) {
        _state.update { state ->
            when (state) {
                is DetailsScreenState.DetailsWithInternet -> state.copy(isLoadingToMemory = isLoading)
                else -> state
            }
        }
    }

    private fun dataInitiation() {
        setPhotoInternal()
        loadThemedPhotos()
    }

    private fun setPhotoInternal(isRefreshing: Boolean = false) {
        if (photo == null) {
            _state.update { DetailsScreenState.Error() }
        } else {
            _state.update {
                if (NetworkMonitorUtil.isConnected.value) {
                    DetailsScreenState.DetailsWithInternet(
                        photo = photo,
                        isRefreshing = isRefreshing,
                        isLiked = it.isLiked
                    )
                } else {
                    DetailsScreenState.DetailsWithoutInternet(
                        photo = photo,
                        isRefreshing = isRefreshing,
                        isLiked = it.isLiked
                    )
                }
            }
        }
    }

    private fun retryPhotoLoad() {
        dataInitiation()
    }

    private fun proceedLikedAction() {
        val photoModel =
            _state.value.photo?.domainModel?.toSimpleModel() ?: _state.value.photo?.simpleModel

        LogPrinter.printLog("!!!!", photoModel.toString())

        if (photoModel == null) {
            _event.emit(
                IDetailsScreenEvent.ShowToast(
                    CommonExceptionModelDomain.UnknownException.toUiString()
                )
            )
            return
        }

        viewModelScope.launch {
            if (_state.value.isLiked) {
                deleteFromDatabaseUseCase.invoke(photo = photoModel)
            } else {
                addToDatabaseUseCase.invoke(photo = photoModel)
            }
        }
    }

    private fun startDownloading(
        resolver: ContentResolver? = null
    ) {
        val photoModel =
            _state.value.photo?.domainModel?.toSimpleModel() ?: _state.value.photo?.simpleModel

        if (photoModel == null) {
            _event.emit(
                IDetailsScreenEvent.ShowToast(
                    CommonExceptionModelDomain.UnknownException.toUiString()
                )
            )
            return
        }

        val fileName = LoaderFunctions.getOriginalFileName(photo = photoModel)

        _loadingToMemoryJob?.cancel()
        _loadingToMemoryJob = viewModelScope.launch {
            resolver?.let {
                changeIsLoadingToMemory(isLoading = true)
                when (
                    val imageLoadingResult =
                        imageLoader.downloadToDevice(
                            resolver = resolver,
                            url = photoModel.photoPictureOriginalSizeUrl,
                            fileName = fileName
                        )
                ) {
                    is ImageOperationResult.Success -> {
                        _event.emit(
                            IDetailsScreenEvent.ShowToast(
                                R.string.download_completed
                            )
                        )
                    }

                    is ImageOperationResult.Error -> {
                        _event.emit(
                            IDetailsScreenEvent.ShowToast(
                                imageLoadingResult.exception
                                    .toCommonException()
                                    .toUiString()
                            )
                        )
                    }
                }
                changeIsLoadingToMemory(isLoading = false)
            }
        }
    }

    private fun navigateBack() {
        _event.emit(
            IDetailsScreenEvent.NavigateBack
        )
    }

    private fun downloadPhoto() {
        viewModelScope.launch {
            _downloadEvent.send(Unit)
        }
    }

    @AssistedFactory
    interface DetailsScreenViewModelFactory {
        fun create(photo: PhotoModelUi?): DetailsScreenViewModel
    }
}