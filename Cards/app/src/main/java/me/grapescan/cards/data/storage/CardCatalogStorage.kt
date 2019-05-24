package me.grapescan.cards.data.storage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.grapescan.cards.R
import me.grapescan.cards.data.Card

class CardCatalogStorage(context: Context, gson: Gson) : Storage<List<Card>> {

    private val cards: List<Card> by lazy {
        val type = object : TypeToken<Map<String, List<CardDao>>>() {}.type
        val sourceJson = context.resources.openRawResource(R.raw.cards)
            .bufferedReader()
            .use { it.readText() }
        val catalog = gson.fromJson<Map<String, List<CardDao>>>(sourceJson, type)
        catalog.values.flatten().map {
            Card(
                id = it.cardId,
                name = it.name,
                imageUrl = it.img ?: ""
            )
        }.filter { it.imageUrl.isNotEmpty() }
    }

    override suspend fun load() = cards

    override suspend fun save(data: List<Card>) {
        throw UnsupportedOperationException("Card catalog is read only.")
    }

}
