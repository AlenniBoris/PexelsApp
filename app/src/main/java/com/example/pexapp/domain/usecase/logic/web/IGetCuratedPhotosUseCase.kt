package com.example.pexapp.domain.usecase.logic.web

import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.domain.model.CustomResultModelDomain
import com.example.pexapp.domain.model.PhotoModelDomain

interface IGetCuratedPhotosUseCase {
    suspend fun invoke(
        page: Int? = null,
        perPage: Int? = null
    ): CustomResultModelDomain<List<PhotoModelDomain>, CommonExceptionModelDomain>
}