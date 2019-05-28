package me.grapescan.cards.api

class JsonCardRepository : CardRepository {

    private val data by lazy { javaClass.getResource("/cards.json").readText() }

    override suspend fun getCards() = data
}