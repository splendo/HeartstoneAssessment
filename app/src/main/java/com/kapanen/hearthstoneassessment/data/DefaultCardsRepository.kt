package com.kapanen.hearthstoneassessment.data

import androidx.lifecycle.LiveData
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.data.local.LocalCardsDataSource
import com.kapanen.hearthstoneassessment.data.remote.RemoteCardsDataSource
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.model.FavouriteItem
import com.kapanen.hearthstoneassessment.model.FilterItem
import com.kapanen.hearthstoneassessment.setting.AppSettings
import com.kapanen.hearthstoneassessment.util.toItemsString

class DefaultCardsRepository(
    private val remoteDataSource: RemoteCardsDataSource,
    private val localDataSource: LocalCardsDataSource,
    private val appSettings: AppSettings
) : CardsRepository {

    override fun observeCards(): LiveData<Result<List<Card>>> =
        localDataSource.observeCards()

    override fun observeCards(cardType: String): LiveData<Result<List<Card>>> =
        localDataSource.observeCards(cardType)

    override fun observeCard(cardId: String): LiveData<Result<Card>> =
        localDataSource.observeCard(cardId)

    override fun observeFavouriteCards(): LiveData<Result<List<Card>>> =
        localDataSource.observeFavouriteCards()

    override suspend fun getCards(): Result<List<Card>> {
        if (!appSettings.isDataInitiallyLoaded) {
            updateCardsFromRemoteDataSource()
        }
        return localDataSource.getCards()
    }

    override suspend fun getCards(cardType: String): Result<List<Card>> {
        if (!appSettings.isDataInitiallyLoaded) {
            updateCardsFromRemoteDataSource()
        }
        return localDataSource.getCards(cardType)
    }

    override suspend fun getCard(cardId: String): Result<Card> = localDataSource.getCard(cardId)

    override suspend fun getFavouriteCards(): Result<List<Card>> =
        localDataSource.getFavouriteCards()

    override suspend fun addFavouriteCard(card: Card) {
        localDataSource.addFavouriteCard(card)
        appSettings.notifyFavoriteUpdate(card.copy(isFavorite = true))
    }

    override suspend fun removeFavouriteCard(card: Card) {
        localDataSource.removeFavouriteCard(card)
        appSettings.notifyFavoriteUpdate(card.copy(isFavorite = false))
    }

    override suspend fun refresh() {
        updateCardsFromRemoteDataSource()
    }

    private suspend fun updateCardsFromRemoteDataSource() {
        val remoteCardsResult = remoteDataSource.getCards()

        if (remoteCardsResult.isSuccess) {
            val cards = remoteCardsResult.getOrDefault(emptyList())
            updateFilterSettings(cards, isFirstLoading = !appSettings.isDataInitiallyLoaded)
            appSettings.isDataInitiallyLoaded = true
            localDataSource.saveCards(cards)
        }
    }

    private fun updateFilterSettings(cards: List<Card>, isFirstLoading: Boolean) {
        val typeSet = mutableSetOf<String>()
        val raritySet = mutableSetOf<String>()
        val classSet = mutableSetOf<String>()
        val mechanicSet = mutableSetOf<String>()

        cards.forEach { card ->
            typeSet.addItem(card.type)
            raritySet.addItem(card.rarity)
            classSet.addItem(card.playerClass)
            card.mechanics?.forEach { mechanicSet.addItem(it.name) }
        }
        typeSet.add(FilterItem.UNDEFINED)
        raritySet.add(FilterItem.UNDEFINED)
        classSet.add(FilterItem.UNDEFINED)
        mechanicSet.add(FilterItem.UNDEFINED)

        appSettings.types = typeSet.toItemsString()
        appSettings.rarities = raritySet.toItemsString()
        appSettings.classes = classSet.toItemsString()
        appSettings.mechanics = mechanicSet.toItemsString()
        if (isFirstLoading) {
            appSettings.typeFilter = appSettings.types
            appSettings.rarityFilter = appSettings.rarities
            appSettings.classFilter = appSettings.classes
            appSettings.mechanicFilter = appSettings.mechanics
        }
    }

    private fun MutableSet<String>.addItem(item: String?) {
        item?.let { this.add(it) }
    }

}
