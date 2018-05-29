package com.nielsmasdorp.heartstone.presentation.card.carousel

import com.nielsmasdorp.heartstone.presentation.card.CardViewModel
import com.nielsmasdorp.heartstone.presentation.card.carousel.CardCarouselFragment.Companion.CARDS_KEY
import javax.inject.Inject

/**
 * Provider that provides a list of cards
 */
class CardsProvider @Inject constructor(private val cardCarouselFragment: CardCarouselFragment) : CardCarousel.CardsProvider {

    override val cards: List<CardViewModel>?
        get() = cardCarouselFragment.arguments?.getParcelableArrayList(CARDS_KEY)
}
