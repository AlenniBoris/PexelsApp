package com.example.pexapp.domain.mapper

import com.example.pexapp.domain.model.CommonExceptionModelDomain
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.toCommonException(): CommonExceptionModelDomain = when (this) {
    is CommonExceptionModelDomain -> this
    is UnknownHostException, is ConnectException ->
        CommonExceptionModelDomain.InternetException
    else -> CommonExceptionModelDomain.UnknownException
}