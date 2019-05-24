package me.grapescan.cards.data

import me.grapescan.cards.data.storage.Storage

class LocalCardRepository(
    private val favoriteStorage: Storage<List<String>>
) : CardRepository {
    override suspend fun getCards() = listOf(
        Card("1", "First Card", "http://wow.zamimg.com/images/hearthstone/cards/enus/original/FP1_014.png"),
        Card("2", "Second Card", "http://wow.zamimg.com/images/hearthstone/cards/enus/original/FP1_014.png"),
        Card("3", "Third Card", "http://wow.zamimg.com/images/hearthstone/cards/enus/original/FP1_014.png"),
        Card("4", "Fourth Card", "http://wow.zamimg.com/images/hearthstone/cards/enus/original/FP1_014.png"),
        Card("5", "Fifth Card", "http://wow.zamimg.com/images/hearthstone/cards/enus/original/FP1_014.png"),
        Card("6", "Sixth Card", "http://wow.zamimg.com/images/hearthstone/cards/enus/original/FP1_014.png")
    ).map { it.copy(isFavorite = it.id in favoriteStorage.getData()) }

    override suspend fun getCard(id: String) = getCards().find { it.id == id }!!

    override suspend fun setFavorite(cardId: String, isFavorite: Boolean) {
        favoriteStorage.setData(favoriteStorage.getData().toMutableList().apply {
            if (isFavorite) {
                add(cardId)
            } else {
                remove(cardId)
            }
        })
    }
}
