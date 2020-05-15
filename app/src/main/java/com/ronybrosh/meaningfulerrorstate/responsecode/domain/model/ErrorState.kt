package com.ronybrosh.meaningfulerrorstate.responsecode.domain.model

sealed class ErrorState(open val message: String?) {
    data class UnknownError(override val message: String?) : ErrorState(message)
    data class NetworkError(override val message: String?) : ErrorState(message)
    data class ServerError(val code: Int, override val message: String) : ErrorState(message)
    data class ClientError(val code: Int, override val message: String) : ErrorState(message)
}