package me.grapescan.cards.data

import me.grapescan.cards.data.storage.Storage

class LocalCardRepository(
    private val favoritesStorage: Storage<List<String>>,
    private val catalogStorage: Storage<List<Card>>
) : CardRepository {

    private var currentSelection: List<Card> = emptyList()

    override suspend fun getCards() = catalogStorage.load()
        .map { it.copy(isFavorite = it.id in favoritesStorage.load()) }

    override suspend fun getCard(id: String) = getCards().find { it.id == id }!!

    override suspend fun setFavorite(cardId: String, isFavorite: Boolean) {
        favoritesStorage.save(favoritesStorage.load().toMutableList().apply {
            if (isFavorite) {
                add(cardId)
            } else {
                remove(cardId)
            }
        })
    }

    override suspend fun setCurrentSelection(items: List<Card>) {
        currentSelection = items
    }

    override suspend fun getCurrentSelection() = currentSelection
}
