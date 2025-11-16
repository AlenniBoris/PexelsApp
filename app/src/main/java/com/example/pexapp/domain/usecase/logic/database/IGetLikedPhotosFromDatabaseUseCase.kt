package com.example.pexapp.domain.usecase.logic.database

import com.example.pexapp.domain.model.PhotoSimpleModelDomain
import kotlinx.coroutines.flow.SharedFlow

interface IGetLikedPhotosFromDatabaseUseCase {
    val likedFlow: SharedFlow<List<PhotoSimpleModelDomain>>
}