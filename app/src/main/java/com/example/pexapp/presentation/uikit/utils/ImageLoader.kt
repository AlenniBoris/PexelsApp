package com.example.pexapp.presentation.uikit.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import com.example.pexapp.domain.model.PhotoSimpleModelDomain
import com.example.pexapp.domain.util.IAppDispatchers
import com.example.pexapp.domain.util.LogPrinter
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


sealed interface ImageOperationResult {
    data object Success : ImageOperationResult
    data class Error(val exception: Throwable) : ImageOperationResult
}

sealed class ImageOperationException : Throwable() {
    data object ResolverException : ImageOperationException()
}

private sealed interface BitmapResult {
    data class Success(val bitmap: Bitmap) : BitmapResult
    data class Error(val exception: Throwable) : BitmapResult
}

object LoaderFunctions {

    fun getOriginalFileName(photo: PhotoSimpleModelDomain): String =
        "original_${photo.id}_${photo.photoPictureOriginalSizeUrl}"

}

class ImageLoader @Inject constructor(
    private val dispatchers: IAppDispatchers
) {

    private fun downloadBitmap(url: String): BitmapResult = runCatching {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()
        val bitmap = connection.inputStream.use { input ->
            BitmapFactory.decodeStream(input)
        }
        BitmapResult.Success(bitmap)
    }.getOrElse {
        LogPrinter.printLog(
            "ImageDownloadUtil", """
            downloadBitmap
                ${it.stackTraceToString()}
        """.trimIndent()
        )
        BitmapResult.Error(it)
    }

    suspend fun downloadToDevice(
        resolver: ContentResolver,
        url: String,
        fileName: String
    ): ImageOperationResult = withContext(dispatchers.IO) {
        val bitmapResult = downloadBitmap(url)
        when (bitmapResult) {
            is BitmapResult.Success -> {

                val bitmap = bitmapResult.bitmap
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                    put(
                        MediaStore.Images.Media.RELATIVE_PATH,
                        "Pictures/MyApp"
                    )
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }

                val uri = resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                ) ?: return@withContext ImageOperationResult.Error(
                    ImageOperationException.ResolverException
                )

                resolver.openOutputStream(uri)?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                }

                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(uri, contentValues, null, null)

                return@withContext ImageOperationResult.Success
            }

            is BitmapResult.Error -> {
                return@withContext ImageOperationResult.Error(bitmapResult.exception)
            }
        }
    }
}