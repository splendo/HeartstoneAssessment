package com.krayem.hearthstone.di

import com.krayem.hearthstone.network.NetworkModule
import com.krayem.hearthstone.ui.main.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector{

    fun inject(mainViewModel: MainViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }

}