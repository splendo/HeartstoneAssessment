package com.nielsmasdorp.domain.card

import com.nielsmasdorp.domain.card.data.CardRepository
import com.nielsmasdorp.domain.card.entity.Card
import com.nielsmasdorp.domain.card.entity.CardRequest
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case that retrieves cards
 */
class GetCards @Inject constructor(private val repository: CardRepository) {

    /**
     * @return a list of cards based on the criteria provided in
     * @param cardRequest
     */
    fun execute(cardRequest: CardRequest): Single<List<Card>> = repository.getCards(cardRequest)
}