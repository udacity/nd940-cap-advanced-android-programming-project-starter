package com.example.android.politicalpreparedness

import android.app.Application
import timber.log.Timber

class PoliticalPreparednessApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}