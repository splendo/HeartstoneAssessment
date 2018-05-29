package com.nielsmasdorp.heartstone

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class HeartstoneApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerHeartstoneAppComponent.builder()
                .heartstoneAppModule(HeartstoneAppModule())
                .create(this)
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}