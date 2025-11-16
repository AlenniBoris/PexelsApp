package com.example.pexapp.domain.usecase.logic.web

import com.example.pexapp.domain.model.CollectionsModelDomain
import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.domain.model.CustomResultModelDomain

interface IGetFeaturedCollectionsUseCase {
    suspend fun invoke(): CustomResultModelDomain<List<CollectionsModelDomain>, CommonExceptionModelDomain>
}