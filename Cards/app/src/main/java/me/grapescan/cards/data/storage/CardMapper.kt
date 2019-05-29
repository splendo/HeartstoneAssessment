package me.grapescan.cards.data.storage

import android.text.Html
import me.grapescan.cards.data.Card
import me.grapescan.cards.data.Mapper

class CardMapper : Mapper<CardDao, Card> {

    companion object {
        private const val NOT_SPECIFIED = "Not specified"
    }

    override fun map(input: CardDao) = Card(
        id = input.cardId,
        cardSet = input.cardSet ?: NOT_SPECIFIED,
        type = input.type ?: NOT_SPECIFIED,
        rarity = input.rarity ?: NOT_SPECIFIED,
        cost = input.cost,
        attack = input.attack,
        health = input.health,
        text = Html.fromHtml(input.text?.replace("\\n", "<br>") ?: ""),
        flavor = input.flavor ?: NOT_SPECIFIED,
        artist = input.artist ?: NOT_SPECIFIED,
        collectible = input.collectible ?: false,
        playerClass = input.playerClass ?: NOT_SPECIFIED,
        howToGetGold = input.howToGetGold ?: NOT_SPECIFIED,
        name = input.name ?: NOT_SPECIFIED,
        imageUrl = input.img ?: "",
        imageGoldUrl = input.imgGold ?: "",
        mechanics = input.mechanics?.map { it.name } ?: emptyList()
    )

}
