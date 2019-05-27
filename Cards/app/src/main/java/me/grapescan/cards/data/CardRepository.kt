package me.grapescan.cards.data

interface CardRepository {
    suspend fun getCard(id: String): Card
    suspend fun getCards(): List<Card>
    suspend fun setFavorite(cardId: String, isFavorite: Boolean)
    suspend fun setCurrentSelection(items: List<Card>)
    suspend fun getCurrentSelection(): List<Card>
}
