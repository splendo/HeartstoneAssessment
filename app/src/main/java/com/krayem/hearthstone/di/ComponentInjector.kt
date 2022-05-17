package com.krayem.hearthstone.di

import com.krayem.hearthstone.db.DatabaseModule
import com.krayem.hearthstone.network.FakeServer
import com.krayem.hearthstone.network.RequestInterceptor
import com.krayem.hearthstone.ui.main.details.CardDetailsViewModel
import com.krayem.hearthstone.ui.main.filter.FiltersViewModel
import com.krayem.hearthstone.ui.main.grid.CardGridViewModel
import com.krayem.hearthstone.ui.main.splash.SplashViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(DatabaseModule::class)])
interface ComponentInjector{

    fun inject(fakeServer: FakeServer)

    fun inject(cardGridViewModel: CardGridViewModel)
    fun inject(cardDetailsViewModel: CardDetailsViewModel)
    fun inject(splashViewModel: SplashViewModel)
    fun inject(filtersViewModel: FiltersViewModel)

    fun inject(requestInterceptor: RequestInterceptor)

    @Component.Builder
    interface Builder {
        fun build(): ComponentInjector

        fun databaseModule(databaseModule: DatabaseModule): Builder

    }

}