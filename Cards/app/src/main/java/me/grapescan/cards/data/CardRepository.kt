package me.grapescan.cards.data

interface CardRepository {
    suspend fun getCard(id: String): Card
    suspend fun getCards(query: Query = Query.ALL): List<Card>
    suspend fun setFavorite(cardId: String, isFavorite: Boolean)
    suspend fun getCurrentSelection(): List<Card>

    enum class Query {
        ALL
    }
}
