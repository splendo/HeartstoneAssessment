package com.nielsmasdorp.heartstone

import com.nielsmasdorp.heartstone.presentation.card.CardsActivityModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    HeartstoneAppModule::class,
    CardsActivityModule::class])
interface HeartstoneAppComponent : AndroidInjector<HeartstoneApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<HeartstoneApp>() {

        abstract fun heartstoneAppModule(heartstoneAppModule: HeartstoneAppModule): Builder
    }
}