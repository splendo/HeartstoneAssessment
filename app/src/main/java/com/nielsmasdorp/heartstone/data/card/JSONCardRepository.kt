package com.nielsmasdorp.heartstone.data.card

import android.app.Application
import com.google.gson.Gson
import com.nielsmasdorp.domain.card.data.CardRepository
import com.nielsmasdorp.domain.card.entity.Card
import com.nielsmasdorp.domain.card.entity.CardRequest
import com.nielsmasdorp.heartstone.data.card.model.JSONCard
import com.nielsmasdorp.heartstone.data.card.model.JSONCardSets
import io.reactivex.Single
import javax.inject.Inject

/**
 * [CardRepository] implementation that fetches cards from a JSON file in /assets
 */
class JSONCardRepository @Inject constructor(private val application: Application) : CardRepository {

    override fun getCards(cardRequest: CardRequest): Single<List<Card>> {
        val json = application.assets.open(CARDS_JSON_FILE_NAME).bufferedReader().use {
            it.readText()
        }
        val cardSets = Gson().fromJson(json, JSONCardSets::class.java)
        return Single.just(getAllCards(cardSets)
                .map { mapToCard(it) }
                .filter { card ->
                    cardRequest.requestedType?.let { card.type == it } ?: true &&
                            cardRequest.requestedRarity?.let { card.rarity == it } ?: true &&
                            cardRequest.requestedClass?.let { card.classes.contains(it) } ?: true &&
                            cardRequest.requestedMechanic?.let { card.mechanics.contains(it) } ?: true
                }
                .let { cards ->
                    when (cardRequest.sortingStrategy) {
                        CardRequest.Sort.ASCENDING -> cards.sortedBy { it.name }
                        CardRequest.Sort.DESCENDING -> cards.sortedByDescending { it.name }
                        CardRequest.Sort.NONE -> cards
                    }
                }
        )
    }

    private fun getAllCards(cardSets: JSONCardSets): List<JSONCard> {
        return listOf(
                cardSets.basicCards,
                cardSets.blackRockMountainCards,
                cardSets.classicCards,
                cardSets.creditsCards,
                cardSets.gadgetzanCards,
                cardSets.goblinsVsGnomesCards,
                cardSets.grandTournamentCards,
                cardSets.hallOfFameCards,
                cardSets.heroSkinsCards,
                cardSets.journeyUnGoroCards,
                cardSets.karazhanCards,
                cardSets.leagueOfExplorersCards
        ).flatten()
    }

    private fun mapToCard(jsonCard: JSONCard): Card {
        return Card(
                id = jsonCard.cardId,
                name = jsonCard.name,
                imgUrl = jsonCard.img,
                cardSet = jsonCard.cardSet,
                type = jsonCard.type,
                rarity = jsonCard.rarity,
                cost = jsonCard.cost,
                attack = jsonCard.attack,
                health = jsonCard.health,
                text = jsonCard.htmlText,
                flavor = jsonCard.flavour,
                classes = jsonCard.classes,
                mechanics = jsonCard.mechanics?.map { it.name }
        )
    }

    private fun List<String>?.contains(value: String): Boolean {
        return this != null && this.contains(value)
    }

    companion object {
        private const val CARDS_JSON_FILE_NAME = "cards.json"
    }
}