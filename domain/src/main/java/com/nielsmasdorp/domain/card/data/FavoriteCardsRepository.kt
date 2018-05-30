package com.nielsmasdorp.domain.card.data

/**
 * Interface for a repository that deals with favorite cards
 */
interface FavoriteCardsRepository {

    /**
     * @param cardId id of the card that should be added to the favorites
     */
    fun addToFavorites(cardId: String)

    /**
     * @param cardId id of the card that should be removed from the favorites
     */
    fun removeFromFavorites(cardId: String)

    /**
     * @param cardId id of the card
     * @return true when the card was added to the favorites, false otherwise
     */
    fun isAddedToFavorites(cardId: String): Boolean
}