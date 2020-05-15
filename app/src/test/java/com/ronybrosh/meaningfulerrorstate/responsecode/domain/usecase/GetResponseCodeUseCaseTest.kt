package com.ronybrosh.meaningfulerrorstate.responsecode.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ronybrosh.meaningfulerrorstate.responsecode.domain.model.ErrorState
import com.ronybrosh.meaningfulerrorstate.responsecode.domain.model.ResultState
import com.ronybrosh.meaningfulerrorstate.responsecode.domain.repository.ResponseCodeRepository
import io.reactivex.Single
import org.junit.Test

class GetResponseCodeUseCaseTest {
    private val responseCodeRepository: ResponseCodeRepository = mock()
    private val sut =
        GetResponseCodeUseCase(
            responseCodeRepository
        )

    @Test
    fun `invoke SHOULD return result state error WHEN repository fail`() {
        val error = ErrorState.NetworkError
        val errorCode = "invalidResponseCode"
        val expected = ResultState.Error<Unit>(error)
        whenever(responseCodeRepository.getResponseCode(errorCode)).thenReturn(Single.just(ResultState.Error(error)))

        val result = sut(errorCode).test()

        result.assertValue(expected)
    }

    @Test
    fun `invoke SHOULD return result state success WHEN repository succeed`() {
        val expected = ResultState.Success(Unit)
        val errorCode = "200"
        whenever(responseCodeRepository.getResponseCode(errorCode)).thenReturn(Single.just(expected))

        val result = sut(errorCode).test()

        result.assertValue(expected)
    }
}