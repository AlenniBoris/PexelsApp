package com.example.pexapp.domain.usecase.logic.database

import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.domain.model.CustomResultModelDomain
import com.example.pexapp.domain.model.PhotoSimpleModelDomain

interface IGetLikedPhotoFromDatabaseByIdUseCase {
    suspend fun invoke(
        id: Int
    ): CustomResultModelDomain<PhotoSimpleModelDomain, CommonExceptionModelDomain>
}