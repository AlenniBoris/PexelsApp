package com.example.pexapp.domain.usecase.implementation.database

import com.example.pexapp.domain.model.PhotoSimpleModelDomain
import com.example.pexapp.domain.repository.IDatabaseRepository
import com.example.pexapp.domain.usecase.logic.database.IGetLikedPhotosFromDatabaseUseCase
import com.example.pexapp.domain.util.IAppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class GetLikedPhotosFromDatabaseUseCaseImpl @Inject constructor(
    private val databaseRepository: IDatabaseRepository,
    private val dispatchers: IAppDispatchers
) : IGetLikedPhotosFromDatabaseUseCase {
    override val likedFlow: SharedFlow<List<PhotoSimpleModelDomain>> =
        databaseRepository.getAllFavourites()
            .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
            .distinctUntilChanged()
            .shareIn(
                scope = CoroutineScope(dispatchers.IO + SupervisorJob()),
                started = SharingStarted.WhileSubscribed(20_000, 0),
                replay = 1
            )
}