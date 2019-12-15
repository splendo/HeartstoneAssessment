package com.krayem.hearthstone

import android.app.Application
import android.content.Context
import com.krayem.hearthstone.network.FakeServer
import com.krayem.hearthstone.objectbox.ObjectBox

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        ObjectBox.init(this)
    }
}