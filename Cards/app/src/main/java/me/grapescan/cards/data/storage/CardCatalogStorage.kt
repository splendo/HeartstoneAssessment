package me.grapescan.cards.data.storage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.grapescan.cards.R
import me.grapescan.cards.data.Card
import me.grapescan.cards.data.Mapper

class CardCatalogStorage(
    context: Context,
    gson: Gson,
    cardMapper: Mapper<CardDao, Card>
) : Storage<List<Card>> {

    private val cards: List<Card> by lazy {
        val type = object : TypeToken<Map<String, List<CardDao>>>() {}.type
        val sourceJson = context.resources.openRawResource(R.raw.cards)
            .bufferedReader()
            .use { it.readText() }
        val catalog = gson.fromJson<Map<String, List<CardDao>>>(sourceJson, type)
        catalog.values.flatten().map(cardMapper)
    }

    override suspend fun load() = cards

    override suspend fun save(data: List<Card>) = throw UnsupportedOperationException("Card catalog is read only.")

}
