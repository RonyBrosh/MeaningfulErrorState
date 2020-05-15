package com.ronybrosh.meaningfulerrorstate.responsecode.domain.usecase

import com.ronybrosh.meaningfulerrorstate.responsecode.domain.model.ResultState
import com.ronybrosh.meaningfulerrorstate.responsecode.domain.repository.ResponseCodeRepository
import io.reactivex.Single

class GetResponseCodeUseCase(private val responseCodeRepository: ResponseCodeRepository) {
    operator fun invoke(responseCode: String): Single<ResultState<Unit>> {
        return responseCodeRepository.getResponseCode(responseCode)
    }
}