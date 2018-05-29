package com.nielsmasdorp.heartstone

import android.app.Application
import android.content.Context
import com.nielsmasdorp.heartstone.generic.dagger.AppContext
import dagger.Module
import dagger.Provides

@Module
class HeartstoneAppModule {

    @Provides
    @AppContext
    fun provideApplicationContext(application: HeartstoneApp): Context = application

    @Provides
    fun provideApplication(application: HeartstoneApp): Application = application
}