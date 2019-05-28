package me.grapescan.cards.data

import me.grapescan.cards.data.storage.Storage

class LocalCardRepository(
    private val favoritesStorage: Storage<List<String>>,
    private val catalogStorage: Storage<List<Card>>
) : CardRepository {

    private var lastQuery: CardRepository.Query = CardRepository.Query()

    override suspend fun getCards(query: CardRepository.Query) = catalogStorage.load()
        .map { it.copy(isFavorite = it.id in favoritesStorage.load()) }
        .filter { it.matches(query.filter) }
        .sortedWith(if (query.sorting.isAscending) {
            compareBy { it.get(query.sorting.parameter) }
        } else {
            compareByDescending { it.get(query.sorting.parameter) }
        })
        .also { lastQuery = query }

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

    override suspend fun getCurrentSelection() = getCards(lastQuery)

    private fun Card.matches(filter: CardRepository.Filter) =
        (filter.mechanics == CardRepository.Mechanics.ANY || filter.mechanics.id in mechanics)
                && (filter.playerClass == CardRepository.PlayerClass.ANY || filter.playerClass.id == playerClass)
                && (filter.rarity == CardRepository.Rarity.ANY || filter.rarity.id == rarity)
                && (filter.type == CardRepository.Type.ANY || filter.type.id == type)
}

private fun Card.get(parameter: CardRepository.Parameter): Comparable<*>? = when (parameter) {
    CardRepository.Parameter.NAME -> name
    CardRepository.Parameter.CARD_SET -> cardSet
    CardRepository.Parameter.TYPE -> type
    CardRepository.Parameter.RARITY -> rarity
    CardRepository.Parameter.COST -> cost
    CardRepository.Parameter.ATTACK -> attack
    CardRepository.Parameter.HEALTH -> health
    CardRepository.Parameter.TEXT -> text.toString()
    CardRepository.Parameter.FLAVOR -> flavor
    CardRepository.Parameter.ARTIST -> artist
    CardRepository.Parameter.COLLECTIBLE -> collectible
    CardRepository.Parameter.PLAYER_CLASS -> playerClass
}
