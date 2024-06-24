package com.example.pexapp.utils

import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.widget.Toast
import androidx.core.net.toUri
import com.example.pexapp.R
import com.google.gson.internal.bind.TypeAdapters.URL
import java.io.ByteArrayOutputStream
import java.net.URL

class Downloader(private val context: Context) {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    fun downloadFile(url: String, fileName: String): Long{
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpeg")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("$fileName.jpg")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$fileName.jpg")

        val result = try {
            Toast.makeText(
                context,
                context.getString(R.string.download_started),
                Toast.LENGTH_SHORT
            ).show()
            downloadManager.enqueue(request)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                context.getString(R.string.download_failed),
                Toast.LENGTH_SHORT
            ).show()
            -1
        }

        return result
    }
}