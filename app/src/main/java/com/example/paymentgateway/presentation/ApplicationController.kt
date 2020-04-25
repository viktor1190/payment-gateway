package com.example.paymentgateway.presentation

import android.app.Application
import com.example.paymentgateway.BuildConfig
import com.example.paymentgateway.presentation.core.ServiceLocator
import com.facebook.stetho.Stetho
import timber.log.Timber

class ApplicationController: Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.appContext = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this);
        }
    }
}