package com.example.pexapp.presentation.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexapp.domain.usecase.logic.database.IGetLikedPhotosFromDatabaseUseCase
import com.example.pexapp.domain.util.SingleFlowEvent
import com.example.pexapp.presentation.model.PhotoModelUi
import com.example.pexapp.presentation.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteScreenViewModel @Inject constructor(
    private val getLikedPhotosFromDatabaseUseCase: IGetLikedPhotosFromDatabaseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavouriteScreenState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<IFavouriteScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        viewModelScope.launch {
            getLikedPhotosFromDatabaseUseCase.likedFlow
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { list ->
                    _state.update {
                        it.copy(
                            photos = list.map { it.toUiModel() }
                        )
                    }
                }
        }
    }

    fun proceedIntent(intent: IFavouriteScreenIntent) {
        when (intent) {
            is IFavouriteScreenIntent.OpenPhoto -> openPhoto(intent.photo)
            is IFavouriteScreenIntent.OpenHomeScreen -> openHomeScreen()
        }
    }

    private fun openHomeScreen() {
        _event.emit(
            IFavouriteScreenEvent.OpenHomeScreen
        )
    }

    private fun openPhoto(photo: PhotoModelUi) {
        _event.emit(
            IFavouriteScreenEvent.OpenPhoto(photo)
        )
    }
}