package com.kapanen.hearthstoneassessment

import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant

@HiltAndroidApp
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initFresco()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
    }

    private fun initFresco() {
        Fresco.initialize(this)
    }

}
