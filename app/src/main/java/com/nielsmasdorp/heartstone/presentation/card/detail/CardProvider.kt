package com.nielsmasdorp.heartstone.presentation.card.detail

import com.nielsmasdorp.heartstone.presentation.card.CardViewModel
import javax.inject.Inject

/**
 * Provider that provides a single card
 */
class CardProvider @Inject constructor(private val cardDetailFragment: CardDetailFragment) : CardDetail.CardProvider {

    override val card: CardViewModel?
        get() = cardDetailFragment.arguments?.getParcelable(CardDetailFragment.CARD_KEY)
}