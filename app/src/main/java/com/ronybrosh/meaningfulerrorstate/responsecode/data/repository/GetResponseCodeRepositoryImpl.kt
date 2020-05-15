package com.ronybrosh.meaningfulerrorstate.responsecode.data.repository

import com.ronybrosh.meaningfulerrorstate.responsecode.data.network.api.ResponseCodeApi
import com.ronybrosh.meaningfulerrorstate.responsecode.data.util.toErrorState
import com.ronybrosh.meaningfulerrorstate.responsecode.domain.model.ResultState
import com.ronybrosh.meaningfulerrorstate.responsecode.domain.repository.ResponseCodeRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GetResponseCodeRepositoryImpl(private val responseCodeApi: ResponseCodeApi) :
    ResponseCodeRepository {
    override fun getResponseCode(responseCode: String): Single<ResultState<Unit>> {
        return responseCodeApi.getResponseCode(responseCode)
            .subscribeOn(Schedulers.io())
            .toSingle<ResultState<Unit>> { ResultState.Success(Unit) }
            .onErrorReturn { throwable ->
                ResultState.Error(throwable.toErrorState())
            }
    }
}