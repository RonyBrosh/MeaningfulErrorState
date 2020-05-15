package com.ronybrosh.meaningfulerrorstate.responsecode.domain.model

sealed class ResultState<T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error<T>(val errorState: ErrorState) : ResultState<T>()
}