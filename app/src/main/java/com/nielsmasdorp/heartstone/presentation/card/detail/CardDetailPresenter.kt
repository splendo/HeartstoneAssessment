package com.nielsmasdorp.heartstone.presentation.card.detail

import com.nielsmasdorp.domain.card.AddCardToFavorites
import com.nielsmasdorp.domain.card.IsCardAddedToFavorites
import com.nielsmasdorp.domain.card.RemoveCardFromFavorites
import javax.inject.Inject

/**
 * Presenter that presents data to the view in the card details
 */
class CardDetailPresenter @Inject constructor(private val view: CardDetail.View,
                                              private val cardProvider: CardDetail.CardProvider,
                                              private val isCardAddedToFavorites: IsCardAddedToFavorites,
                                              private val removeCardFromFavorites: RemoveCardFromFavorites,
                                              private val addCardToFavorites: AddCardToFavorites) : CardDetail.Presenter {

    override fun startPresenting() {
        cardProvider.card?.let {
            view.card = it
            if (isCardAddedToFavorites.execute(it.id)) view.showCardAsFavorite(notifyUser = false)
        }
    }

    override fun onAddToFavoritesClicked() {
        view.card?.let {
            if (isCardAddedToFavorites.execute(it.id)) {
                removeCardFromFavorites.execute(it.id)
                view.showCardNotFavorite()
            } else {
                addCardToFavorites.execute(it.id)
                view.showCardAsFavorite()
            }
        }
    }
}