package com.ronybrosh.meaningfulerrorstate.responsecode.presentation.view

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.ronybrosh.meaningfulerrorstate.R
import com.ronybrosh.meaningfulerrorstate.responsecode.domain.model.ErrorState
import com.ronybrosh.meaningfulerrorstate.responsecode.domain.model.ResultState
import com.ronybrosh.meaningfulerrorstate.responsecode.domain.usecase.GetResponseCodeUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_get_response_code.*
import org.koin.android.ext.android.inject

class ResponseCodeActivity : AppCompatActivity() {
    private val getResponseCodeUseCase: GetResponseCodeUseCase by inject()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_response_code)
        getResponseCodeButton.setOnClickListener { getResponseCode() }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun getResponseCode() {
        val disposable =
            getResponseCodeUseCase(responseCodeEditText.text.toString()).observeOn(AndroidSchedulers.mainThread())
                .subscribe { resultState ->
                    when (resultState) {
                        is ResultState.Success -> updateResponseCodeText(
                            getString(R.string.get_response_code_success),
                            R.color.colorSuccess
                        )
                        is ResultState.Error -> {
                            updateResponseCodeText(
                                resultState.errorState.javaClass.simpleName.plus(":\n")
                                    .plus(resultState.errorState.message),
                                R.color.colorFailure
                            )
                            if (resultState.errorState is ErrorState.NetworkError) {
                                Toast.makeText(
                                    this,
                                    getString(R.string.get_response_code_network_error_toast),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
        compositeDisposable.clear()
        compositeDisposable.add(disposable)
    }

    private fun updateResponseCodeText(text: String, @ColorRes colourResourceId: Int) {
        responseCodeTextView.text = SpannableString(text).apply {
            setSpan(
                ForegroundColorSpan(
                    ResourcesCompat.getColor(
                        resources,
                        colourResourceId,
                        null
                    )
                ), 0, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
        }
    }
}
