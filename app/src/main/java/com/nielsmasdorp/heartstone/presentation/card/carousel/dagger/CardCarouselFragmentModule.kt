package com.nielsmasdorp.heartstone.presentation.card.carousel.dagger

import android.support.v4.app.Fragment
import com.nielsmasdorp.heartstone.presentation.card.carousel.CardCarousel
import com.nielsmasdorp.heartstone.presentation.card.carousel.CardCarouselFragment
import com.nielsmasdorp.heartstone.presentation.card.carousel.CardCarouselPresenter
import com.nielsmasdorp.heartstone.presentation.card.carousel.CardsProvider
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [CardCarouselSubcomponent::class])
abstract class CardCarouselFragmentModule {

    @Binds
    @IntoMap
    @FragmentKey(CardCarouselFragment::class)
    internal abstract fun bindCardCarouselFragmentInjectorFactory(builder: CardCarouselSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    abstract fun bindCardCarouselView(cardCarouselFragment: CardCarouselFragment): CardCarousel.View

    @Binds
    abstract fun bindCardCarouselPresenter(cardCarouselPresenter: CardCarouselPresenter): CardCarousel.Presenter

    @Binds
    abstract fun bindCardsProvider(cardsProvider: CardsProvider): CardCarousel.CardsProvider
}