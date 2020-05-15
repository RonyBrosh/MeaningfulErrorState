package com.ronybrosh.meaningfulerrorstate.responsecode.domain.repository

import com.ronybrosh.meaningfulerrorstate.responsecode.domain.model.ResultState
import io.reactivex.Single

interface ResponseCodeRepository {
    fun getResponseCode(responseCode: String): Single<ResultState<Unit>>
}