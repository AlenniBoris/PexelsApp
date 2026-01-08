package com.example.pexapp.data.repository

import com.example.pexapp.data.mappers.asEntityModelData
import com.example.pexapp.data.mappers.asPhotoSimpleDomainModel
import com.example.pexapp.domain.mapper.toCommonException
import com.example.pexapp.data.source.dao.FavouritesDatabase
import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.domain.model.CustomResultModelDomain
import com.example.pexapp.domain.model.PhotoSimpleModelDomain
import com.example.pexapp.domain.repository.IDatabaseRepository
import com.example.pexapp.domain.util.IAppDispatchers
import com.example.pexapp.domain.util.LogPrinter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val database: FavouritesDatabase,
    private val dispatchers: IAppDispatchers
) : IDatabaseRepository {

    override suspend fun addPhoto(
        photo: PhotoSimpleModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {
                database.dao.addPhoto(photoEntity = photo.asEntityModelData())
                return@withContext CustomResultModelDomain.Success(Unit)
            }.getOrElse { exception ->
                LogPrinter.printLog(
                    tag = "!!!!",
                    message = """
                        DatabaseRepositoryImpl-addPhoto
                        
                        ${exception.stackTraceToString()}
                        """.trimIndent()
                )
                return@withContext CustomResultModelDomain.Error(exception.toCommonException())
            }
        }

    override suspend fun deletePhoto(
        photo: PhotoSimpleModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {
                database.dao.deletePhoto(photo.asEntityModelData())
                return@withContext CustomResultModelDomain.Success(Unit)
            }.getOrElse { exception ->
                LogPrinter.printLog(
                    tag = "!!!!",
                    message = """
                        DatabaseRepositoryImpl-deletePhoto
                        
                        ${exception.stackTraceToString()}
                        """.trimIndent()
                )
                return@withContext CustomResultModelDomain.Error(exception.toCommonException())
            }
        }

    override fun getAllFavourites(): Flow<List<PhotoSimpleModelDomain>> =
        database.dao.getAllFavourites().map { list -> list.map { it.asPhotoSimpleDomainModel() } }

    override suspend fun getFavouriteById(
        id: Int
    ): CustomResultModelDomain<PhotoSimpleModelDomain, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {
                val res = database.dao.getFavouriteById(id).asPhotoSimpleDomainModel()
                return@withContext CustomResultModelDomain.Success(res)
            }.getOrElse { exception ->
                LogPrinter.printLog(
                    tag = "!!!!",
                    message = """
                        DatabaseRepositoryImpl-getFavouriteById
                        
                        ${exception.stackTraceToString()}
                        """.trimIndent()
                )
                return@withContext CustomResultModelDomain.Error(exception.toCommonException())
            }
        }
}