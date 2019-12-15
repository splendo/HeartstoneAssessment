package com.krayem.hearthstone.di

import com.krayem.hearthstone.db.DatabaseModule
import com.krayem.hearthstone.network.FakeServer
import com.krayem.hearthstone.network.NetworkModule
import com.krayem.hearthstone.network.RequestInterceptor
import com.krayem.hearthstone.ui.main.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(DatabaseModule::class)])
interface ComponentInjector{

    fun inject(fakeServer: FakeServer)

    fun inject(requestInterceptor: RequestInterceptor)

    @Component.Builder
    interface Builder {
        fun build(): ComponentInjector

        fun databaseModule(databaseModule: DatabaseModule): Builder

    }

}