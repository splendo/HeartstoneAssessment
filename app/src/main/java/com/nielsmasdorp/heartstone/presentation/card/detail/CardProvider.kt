package com.nielsmasdorp.heartstone.presentation.card.detail

import com.nielsmasdorp.heartstone.presentation.card.CardViewModel
import javax.inject.Inject

/**
 * [CardDetail.CardProvider] implementation that provides a single card through fragment arguments
 */
class CardProvider @Inject constructor(private val cardDetailFragment: CardDetailFragment) : CardDetail.CardProvider {

    override val card: CardViewModel?
        get() = cardDetailFragment.arguments?.getParcelable(CardDetailFragment.CARD_KEY)
}