package com.example.paymentgateway.presentation

import android.app.Application
import com.example.paymentgateway.BuildConfig
import timber.log.Timber

class ApplicationController: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}