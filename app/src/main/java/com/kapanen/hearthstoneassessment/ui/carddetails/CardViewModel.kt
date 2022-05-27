package com.kapanen.hearthstoneassessment.ui.carddetails

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.core.text.HtmlCompat
import androidx.lifecycle.*
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val MECHANICS_ITEM_DELIMITER = ", "

@HiltViewModel
class CardViewModel @Inject constructor(
    private val cardsRepository: CardsRepository
) : ViewModel() {

    fun observeItems(card: Card, resources: Resources): LiveData<List<Any>> {
        return cardsRepository.observeCard(card.cardId)
            .map {
                it.getOrNull()?.let { newCard -> createItems(newCard, resources) } ?: emptyList()
            }
    }

    fun getItems(card: Card, resources: Resources) = createItems(card, resources)

    private fun createItems(
        card: Card,
        resources: Resources
    ): List<Any> {
        val items = mutableListOf<Any>()
        items.add(CardImageItem(title = card.name, img = card.img))
        items.addStringItem(R.string.card_stat_card_set, card.cardSet, resources)
        items.addStringItem(R.string.card_stat_type, card.type, resources)
        items.addStringItem(R.string.card_stat_faction, card.faction, resources)
        items.addStringItem(R.string.card_stat_rarity, card.rarity, resources)
        items.addIntItem(R.string.card_stat_cost, card.cost, resources)
        items.addIntItem(R.string.card_stat_attack, card.attack, resources)
        items.addIntItem(R.string.card_stat_health, card.health, resources)
        items.addStringItem(
            R.string.card_stat_text,
            if (!card.htmlText.isNullOrBlank()) {
                HtmlCompat.fromHtml(card.htmlText.orEmpty(), HtmlCompat.FROM_HTML_MODE_LEGACY)
                    .toString()
            } else {
                null
            },
            resources
        )
        items.addStringItem(R.string.card_stat_flavor, card.flavor, resources)
        items.addStringItem(
            R.string.card_stat_collectible,
            card.collectible?.toString(),
            resources
        )
        items.addStringItem(R.string.card_stat_elite, card.elite?.toString(), resources)
        items.addStringItem(R.string.card_stat_player_class, card.playerClass, resources)
        items.addStringItem(
            R.string.card_stat_multi_class_group,
            card.multiClassGroup,
            resources
        )
        items.addStringItem(
            R.string.card_stat_mechanics,
            card.mechanics?.map { it.name }?.joinToString(separator = MECHANICS_ITEM_DELIMITER),
            resources
        )
        items.add(FavouriteItem(card))
        return items.toList()
    }

    private fun MutableList<Any>.addStringItem(
        @StringRes labelRes: Int,
        value: String?,
        resources: Resources
    ) {
        value?.let { add(CardStringStatItem(resources.getString(labelRes), it)) }
    }

    private fun MutableList<Any>.addIntItem(
        @StringRes labelRes: Int,
        value: Int?,
        resources: Resources
    ) {
        value?.let { add(CardIntStatItem(resources.getString(labelRes), it)) }
    }

}
