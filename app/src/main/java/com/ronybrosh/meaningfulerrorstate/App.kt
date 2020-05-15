package com.ronybrosh.meaningfulerrorstate

import android.app.Application
import com.ronybrosh.meaningfulerrorstate.di.networkModule
import com.ronybrosh.meaningfulerrorstate.responsecode.di.getResponseCodeModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(networkModule, getResponseCodeModule))
    }
}