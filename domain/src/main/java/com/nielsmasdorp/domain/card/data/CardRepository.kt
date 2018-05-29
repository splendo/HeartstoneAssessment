package com.nielsmasdorp.domain.card.data

import com.nielsmasdorp.domain.card.entity.Card
import com.nielsmasdorp.domain.card.entity.CardRequest
import io.reactivex.Single

/**
 * Interface for an repository that can provide a list of [Card]s
 */
interface CardRepository {

    /**
     * @return a list of cards based on the criteria provided in
     * @param cardRequest
     */
    fun getCards(cardRequest: CardRequest): Single<List<Card>>
}