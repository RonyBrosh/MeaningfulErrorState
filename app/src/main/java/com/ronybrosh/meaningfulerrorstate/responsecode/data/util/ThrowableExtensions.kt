package com.ronybrosh.meaningfulerrorstate.responsecode.data.util

import com.ronybrosh.meaningfulerrorstate.responsecode.domain.model.ErrorState
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toErrorState(): ErrorState {
    return when (this) {
        is HttpException -> {
            when {
                code() in 400..499 -> ErrorState.ClientError(code(), localizedMessage ?: message())
                else -> ErrorState.ServerError(code(), localizedMessage ?: message())
            }
        }
        is IOException -> ErrorState.NetworkError(localizedMessage)
        else -> ErrorState.UnknownError(localizedMessage)
    }
}