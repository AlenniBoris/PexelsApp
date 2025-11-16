package com.example.pexapp.domain.usecase.logic.web

import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.domain.model.CustomResultModelDomain
import com.example.pexapp.domain.model.PhotoModelDomain

interface IGetPhotoByIdUseCase {
    suspend fun invoke(
        id: Int
    ): CustomResultModelDomain<PhotoModelDomain?, CommonExceptionModelDomain>
}