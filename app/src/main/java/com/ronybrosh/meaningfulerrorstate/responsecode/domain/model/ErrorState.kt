package com.ronybrosh.meaningfulerrorstate.responsecode.domain.model

sealed class ErrorState {
    object UnknownError : ErrorState()
    object NetworkError : ErrorState()
    data class ServerError(val message: String) : ErrorState()
    data class ClientError(val message: String) : ErrorState()
}