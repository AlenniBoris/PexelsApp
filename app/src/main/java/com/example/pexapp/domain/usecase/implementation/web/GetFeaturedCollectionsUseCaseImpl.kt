package com.example.pexapp.domain.usecase.implementation.web

import com.example.pexapp.domain.model.CollectionsModelDomain
import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.domain.model.CustomResultModelDomain
import com.example.pexapp.domain.repository.IWebDataRepository
import com.example.pexapp.domain.usecase.logic.web.IGetFeaturedCollectionsUseCase
import com.example.pexapp.domain.util.IAppDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFeaturedCollectionsUseCaseImpl @Inject constructor(
    private val webDataRepository: IWebDataRepository,
    private val dispatchers: IAppDispatchers
) : IGetFeaturedCollectionsUseCase {
    override suspend fun invoke(): CustomResultModelDomain<List<CollectionsModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext webDataRepository.getFeaturedCollectionsList()
        }
}