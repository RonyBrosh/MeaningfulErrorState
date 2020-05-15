package com.ronybrosh.meaningfulerrorstate.responsecode.data.repository

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ronybrosh.meaningfulerrorstate.responsecode.data.network.api.ResponseCodeApi
import com.ronybrosh.meaningfulerrorstate.responsecode.domain.model.ErrorState
import com.ronybrosh.meaningfulerrorstate.responsecode.domain.model.ResultState
import io.reactivex.Completable
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class GetResponseCodeRepositoryImplTest {
    private val responseCodeApi: ResponseCodeApi = mock()
    private val sut =
        GetResponseCodeRepositoryImpl(
            responseCodeApi
        )

    @Test
    fun `getResponseCode SHOULD return result state error with error state client error WHEN api fail with http exception AND 400 error code`() {
        val error = HttpException(Response.error<Unit>(400, mock()))
        val errorCode = "400"
        val expected = ResultState.Error<Unit>(ErrorState.ClientError(400, error.localizedMessage ?: error.message()))
        whenever(responseCodeApi.getResponseCode(errorCode)).thenReturn(Completable.error(error))

        val result = sut.getResponseCode(errorCode).blockingGet()

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `getResponseCode SHOULD return result state error with error state server error WHEN api fail with http exception AND 500 error code`() {
        val error = HttpException(Response.error<Unit>(500, mock()))
        val errorCode = "500"
        val expected = ResultState.Error<Unit>(ErrorState.ServerError(500, error.localizedMessage ?: error.message()))
        whenever(responseCodeApi.getResponseCode(errorCode)).thenReturn(Completable.error(error))

        val result = sut.getResponseCode(errorCode).blockingGet()

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `getResponseCode SHOULD return result state error with error state network error WHEN api fail with io exception`() {
        val error = IOException()
        val errorCode = "200"
        val expected = ResultState.Error<Unit>(ErrorState.NetworkError(error.localizedMessage ?: error.message))
        whenever(responseCodeApi.getResponseCode(errorCode)).thenReturn(Completable.error(error))

        val result = sut.getResponseCode(errorCode).blockingGet()

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `getResponseCode SHOULD return result state error with error state unknown error WHEN api fail with just exception`() {
        val error = Exception()
        val errorCode = "200"
        val expected = ResultState.Error<Unit>(ErrorState.UnknownError(error.localizedMessage ?: error.message))
        whenever(responseCodeApi.getResponseCode(errorCode)).thenReturn(Completable.error(error))

        val result = sut.getResponseCode(errorCode).blockingGet()

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `getResponseCode SHOULD return result state success with mapped value WHEN api succeed`() {
        val expected = ResultState.Success(Unit)
        val errorCode = "200"
        whenever(responseCodeApi.getResponseCode(errorCode)).thenReturn(Completable.complete())

        val result = sut.getResponseCode(errorCode).blockingGet()

        assertThat(result).isEqualTo(expected)
    }
}