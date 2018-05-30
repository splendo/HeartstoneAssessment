package com.nielsmasdorp.heartstone.presentation.card.carousel.dagger

import com.nielsmasdorp.heartstone.presentation.card.carousel.CardCarouselFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface CardCarouselSubcomponent : AndroidInjector<CardCarouselFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CardCarouselFragment>()
}