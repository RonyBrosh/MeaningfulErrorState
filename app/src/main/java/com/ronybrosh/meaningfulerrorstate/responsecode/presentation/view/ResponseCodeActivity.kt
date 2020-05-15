package com.ronybrosh.meaningfulerrorstate.responsecode.presentation.view

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
                        is ResultState.Success -> handleSuccessResponse()
                        is ResultState.Error -> {
                            when (resultState.errorState) {
                                is ErrorState.UnknownError -> handleUnknownError()
                                is ErrorState.NetworkError -> handleNetworkError()
                                is ErrorState.ServerError -> handleServerError(resultState.errorState.message)
                                is ErrorState.ClientError -> handleClientError(resultState.errorState.message)
                            }
                        }
                    }
                }
        compositeDisposable.clear()
        compositeDisposable.add(disposable)
    }

    private fun handleSuccessResponse() {
        with(AlertDialog.Builder(this).create()) {
            setMessage(getString(R.string.get_response_code_success_message))
            show()
        }
    }

    private fun handleUnknownError() {
        Toast.makeText(
            this,
            getString(R.string.get_response_code_unknown_error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun handleNetworkError() {
        with(AlertDialog.Builder(this).create()) {
            setTitle(getString(R.string.get_response_code_error_dialog_title_network))
            setMessage(getString(R.string.get_response_code_network_error_message))
            show()
        }
    }

    private fun handleServerError(message: String) {
        with(AlertDialog.Builder(this).create()) {
            setTitle(getString(R.string.get_response_code_error_dialog_title_server))
            setMessage(message)
            setButton(
                AlertDialog.BUTTON_NEUTRAL,
                getString(R.string.get_response_code_error_dialog_got_it_button)
            ) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            show()
        }
    }

    private fun handleClientError(message: String) {
        with(AlertDialog.Builder(this).create()) {
            setTitle(getString(R.string.get_response_code_error_dialog_title_client))
            setMessage(message)
            setButton(
                AlertDialog.BUTTON_NEGATIVE,
                getString(R.string.get_response_code_error_dialog_close_button)
            ) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            setButton(
                AlertDialog.BUTTON_POSITIVE,
                getString(R.string.get_response_code_error_dialog_retry_button)
            ) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            show()
        }
    }
}
