package com.ronybrosh.meaningfulerrorstate.responsecode.di

import com.ronybrosh.meaningfulerrorstate.responsecode.data.network.api.ResponseCodeApi
import com.ronybrosh.meaningfulerrorstate.responsecode.data.repository.GetResponseCodeRepositoryImpl
import com.ronybrosh.meaningfulerrorstate.responsecode.domain.repository.ResponseCodeRepository
import com.ronybrosh.meaningfulerrorstate.responsecode.domain.usecase.GetResponseCodeUseCase
import org.koin.dsl.module.module
import retrofit2.Retrofit

val getResponseCodeModule = module(override = true) {
    factory<ResponseCodeRepository> { GetResponseCodeRepositoryImpl(get()) }
    factory { provideResponseCodeApi(get()) }
    factory { GetResponseCodeUseCase(get()) }
}

fun provideResponseCodeApi(retrofit: Retrofit): ResponseCodeApi = retrofit.create(ResponseCodeApi::class.java)
