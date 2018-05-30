package com.nielsmasdorp.heartstone.presentation.card.carousel

import com.nielsmasdorp.heartstone.presentation.card.CardViewModel

/**
 * Contract for the card carousel feature
 */
interface CardCarousel {

    interface View {

        var cards: List<CardViewModel>
    }

    interface Presenter {

        fun startPresenting()
    }

    interface CardsProvider {

        val cards: List<CardViewModel>?
    }
}
