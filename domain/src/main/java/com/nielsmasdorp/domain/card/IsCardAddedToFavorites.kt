package com.nielsmasdorp.domain.card

import com.nielsmasdorp.domain.card.data.FavoriteCardsRepository
import javax.inject.Inject

/**
 * Use case that checks if a card id was added to favorites
 */
class IsCardAddedToFavorites @Inject constructor(private val favoriteCardsRepository: FavoriteCardsRepository) {

    /**
     * @param cardId id of the card that can be in favorites
     * @return true when the id exists in favorites
     */
    fun execute(cardId: String) = favoriteCardsRepository.isAddedToFavorites(cardId)
}