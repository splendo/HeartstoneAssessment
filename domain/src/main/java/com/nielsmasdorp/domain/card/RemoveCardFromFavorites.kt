package com.nielsmasdorp.domain.card

import com.nielsmasdorp.domain.card.data.FavoriteCardsRepository
import javax.inject.Inject

/**
 * Use case that adds a card id to the favorites
 */
class RemoveCardFromFavorites @Inject constructor(private val favoriteCardsRepository: FavoriteCardsRepository) {

    /**
     * @param cardId id that should be added to the favorites
     */
    fun execute(cardId: String) = favoriteCardsRepository.removeFromFavorites(cardId)
}