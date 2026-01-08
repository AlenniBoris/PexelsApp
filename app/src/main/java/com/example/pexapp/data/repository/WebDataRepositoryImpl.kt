package com.example.pexapp.data.repository

import com.example.pexapp.data.mappers.asCollectionsModelDomain
import com.example.pexapp.data.mappers.asPhotoDomainModel
import com.example.pexapp.domain.mapper.toCommonException
import com.example.pexapp.data.source.api.ApiServiceValues
import com.example.pexapp.data.source.api.PhotoApiService
import com.example.pexapp.domain.model.CollectionsModelDomain
import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.domain.model.CustomResultModelDomain
import com.example.pexapp.domain.model.PhotoModelDomain
import com.example.pexapp.domain.repository.IWebDataRepository
import com.example.pexapp.domain.util.IAppDispatchers
import com.example.pexapp.domain.util.LogPrinter
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WebDataRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
    private val dispatchers: IAppDispatchers
) : IWebDataRepository {

    override suspend fun getPhotoById(
        id: Int
    ): CustomResultModelDomain<PhotoModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {
                val result = photoApiService.getPhotoById(id).asPhotoDomainModel()
                return@withContext CustomResultModelDomain.Success(
                    result
                )
            }.getOrElse { exception ->
                LogPrinter.printLog(
                    tag = "!!!!",
                    message = """
                        WebDataRepositoryImpl-getPhotoById
                        
                        ${exception.stackTraceToString()}
                        """.trimIndent()
                )
                return@withContext CustomResultModelDomain.Error(
                    exception.toCommonException()
                )
            }
        }

    override suspend fun getCuratedPhotosList(
        page: Int?,
        perPage: Int?
    ): CustomResultModelDomain<List<PhotoModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {
                val listOfPhotos =
                    photoApiService.getCuratedPhotos(
                        page ?: ApiServiceValues.BASE_PAGE_NUMBER,
                        perPage ?: ApiServiceValues.BASE_NUMBER_PER_PAGE
                    ).photos?.mapNotNull { it?.asPhotoDomainModel() }

                return@withContext listOfPhotos?.let {
                    CustomResultModelDomain.Success(listOfPhotos)
                } ?: CustomResultModelDomain.Error(
                    CommonExceptionModelDomain.ErrorGettingData
                )
            }.getOrElse { exception ->
                LogPrinter.printLog(
                    tag = "!!!!",
                    message = """
                        WebDataRepositoryImpl-getCuratedPhotosList
                        
                        ${exception.stackTraceToString()}
                        """.trimIndent()
                )
                return@withContext CustomResultModelDomain.Error(
                    exception.toCommonException()
                )
            }
        }

    override suspend fun getSearchedPhotosList(
        query: String,
        page: Int?,
        perPage: Int?
    ): CustomResultModelDomain<List<PhotoModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {
                val listOfPhotos =
                    photoApiService.getSearchedPhotos(
                        query,
                        page ?: ApiServiceValues.BASE_PAGE_NUMBER,
                        perPage ?: ApiServiceValues.BASE_NUMBER_PER_PAGE
                    ).photos?.mapNotNull { it?.asPhotoDomainModel() }

                return@withContext listOfPhotos?.let {
                    CustomResultModelDomain.Success(listOfPhotos)
                } ?: CustomResultModelDomain.Error(
                    CommonExceptionModelDomain.ErrorGettingData
                )
            }.getOrElse { exception ->
                LogPrinter.printLog(
                    tag = "!!!!",
                    message = """
                        WebDataRepositoryImpl-getSearchedPhotosList
                        
                        ${exception.stackTraceToString()}
                        """.trimIndent()
                )
                return@withContext CustomResultModelDomain.Error(
                    exception.toCommonException()
                )
            }
        }

    override suspend fun getFeaturedCollectionsList(): CustomResultModelDomain<List<CollectionsModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {
                val listOfCollections =
                    photoApiService.getFeaturedCollections()
                        .collections
                        ?.mapNotNull { it?.asCollectionsModelDomain() }

                return@withContext listOfCollections?.let {
                    CustomResultModelDomain.Success(listOfCollections)
                } ?: CustomResultModelDomain.Error(
                    CommonExceptionModelDomain.ErrorGettingData
                )
            }.getOrElse { exception ->
                LogPrinter.printLog(
                    tag = "!!!!",
                    message = """
                        WebDataRepositoryImpl-getFeaturedCollectionsList
                        
                        ${exception.stackTraceToString()}
                        """.trimIndent()
                )
                return@withContext CustomResultModelDomain.Error(
                    exception.toCommonException()
                )
            }
        }
}