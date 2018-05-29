package com.nielsmasdorp.heartstone.presentation.card.carousel

import javax.inject.Inject

/**
 * Presenter that presents data for the card carousel view
 */
class CardCarouselPresenter @Inject constructor(private val view: CardCarousel.View,
                                                private val cardsProvider: CardCarousel.CardsProvider) : CardCarousel.Presenter {

    override fun startPresenting() {
        cardsProvider.cards?.let {
            view.cards = it
        }
    }
}