package com.nielsmasdorp.heartstone.presentation.card

import com.nielsmasdorp.heartstone.presentation.card.carousel.dagger.CardCarouselFragmentModule
import com.nielsmasdorp.heartstone.presentation.card.detail.dagger.CardDetailFragmentModule
import com.nielsmasdorp.heartstone.presentation.card.grid.dagger.CardGridFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CardsActivityModule {

    @ContributesAndroidInjector(modules = [CardGridFragmentModule::class, CardCarouselFragmentModule::class, CardDetailFragmentModule::class])
    abstract fun cardsActivity(): CardsActivity
}