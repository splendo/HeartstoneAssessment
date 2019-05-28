package me.grapescan.cards.data.storage

import android.content.Context
import android.text.Html
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.grapescan.cards.R
import me.grapescan.cards.data.Card

class CardCatalogStorage(context: Context, gson: Gson) : Storage<List<Card>> {

    companion object {
        private const val NOT_SPECIFIED = "Not specified"
    }

    private val cards: List<Card> by lazy {
        val type = object : TypeToken<Map<String, List<CardDao>>>() {}.type
        val sourceJson = context.resources.openRawResource(R.raw.cards)
            .bufferedReader()
            .use { it.readText() }
        val catalog = gson.fromJson<Map<String, List<CardDao>>>(sourceJson, type)
        catalog.values.flatten().map {
            Card(
                id = it.cardId,
                cardSet = it.cardSet ?: NOT_SPECIFIED,
                type = it.type ?: NOT_SPECIFIED,
                rarity = it.rarity ?: NOT_SPECIFIED,
                cost = it.cost,
                attack = it.attack,
                health = it.health,
                text = Html.fromHtml(it.text?.replace("\\n", "<br>") ?: ""),
                flavor = it.flavor ?: NOT_SPECIFIED,
                artist = it.artist ?: NOT_SPECIFIED,
                collectible = it.collectible ?: false,
                playerClass = it.playerClass ?: NOT_SPECIFIED,
                howToGetGold = it.howToGetGold ?: NOT_SPECIFIED,
                name = it.name ?: NOT_SPECIFIED,
                imageUrl = it.img ?: "",
                imageGoldUrl = it.imgGold ?: "",
                mechanics = it.mechanics?.map { it.name } ?: emptyList()
            )
        }
    }

    override suspend fun load() = cards

    override suspend fun save(data: List<Card>) = throw UnsupportedOperationException("Card catalog is read only.")

}
