package com.nielsmasdorp.heartstone.presentation.card.grid

import com.nielsmasdorp.heartstone.presentation.card.CardViewModel

/**
 * Contract for the card grid feature
 */
interface CardGrid {

    interface View {

        var cards: List<CardViewModel>
    }

    interface Presenter {

        fun startPresenting()
        fun stopPresenting()
        fun openCardDetails(cardViewModel: CardViewModel)
    }

    interface Navigator {

        fun openCardDetails(cardViewModel: CardViewModel)
    }

    interface StringProvider {

        fun getUnknownAttributeString(): String
    }
}
