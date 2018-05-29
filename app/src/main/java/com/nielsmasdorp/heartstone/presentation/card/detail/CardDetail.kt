package com.nielsmasdorp.heartstone.presentation.card.detail

import com.nielsmasdorp.heartstone.presentation.card.CardViewModel

/**
 * Contract for the card details feature
 */
interface CardDetail {

    interface View {

        var card: CardViewModel?
        fun showCardAsFavorite(notifyUser: Boolean = true)
        fun showCardNotFavorited()
    }

    interface Presenter {

        fun startPresenting()
        fun onAddToFavoritesClicked()
    }

    interface CardProvider {

        val card: CardViewModel?
    }
}
