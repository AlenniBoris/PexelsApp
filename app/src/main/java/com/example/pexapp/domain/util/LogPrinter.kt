package com.example.pexapp.domain.util

import android.util.Log
import com.example.pexapp.BuildConfig

object LogPrinter {
    fun printLog(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            runCatching {
                Log.e(tag, message)
            }.getOrElse {
                println(
                    """
                        $tag
                        --------------
                        $message
                    """.trimIndent()
                )
            }
        }
    }
}