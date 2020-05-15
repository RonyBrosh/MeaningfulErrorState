package com.ronybrosh.meaningfulerrorstate.responsecode.data.network.api

import io.reactivex.Completable
import retrofit2.http.GET
import retrofit2.http.Path

interface ResponseCodeApi {
    @GET("{responseCode}")
    fun getResponseCode(@Path("responseCode") responseCode: String): Completable
}