package com.kapanen.hearthstoneassessment.data

import androidx.lifecycle.LiveData
import com.kapanen.hearthstoneassessment.data.local.LocalCardsDataSource
import com.kapanen.hearthstoneassessment.data.remote.RemoteCardsDataSource
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.setting.AppSettings

class DefaultCardsRepository(
    private val remoteDataSource: RemoteCardsDataSource,
    private val localDataSource: LocalCardsDataSource,
    private val appSettings: AppSettings
) : CardsRepository {

    override fun observeCards(): LiveData<Result<List<Card>>> =
        localDataSource.observeCards()

    override fun observeCards(cardType: String): LiveData<Result<List<Card>>> =
        localDataSource.observeCards(cardType)

    override fun observeFavouriteCards(): LiveData<Result<List<Card>>> =
        localDataSource.observeFavouriteCards()

    override suspend fun getCards(): Result<List<Card>> {
        if (!appSettings.isDataInitiallyLoaded) {
            updateCardsFromRemoteDataSource()
        }
        return localDataSource.getCards()
    }

    override suspend fun getCards(cardGroupName: String): Result<List<Card>> {
        if (!appSettings.isDataInitiallyLoaded) {
            updateCardsFromRemoteDataSource()
        }
        return localDataSource.getCards(cardGroupName)
    }

    override suspend fun getFavouriteCards(): Result<List<Card>> =
        localDataSource.getFavouriteCards()

    override suspend fun addFavouriteCard(card: Card) {
        localDataSource.addFavouriteCard(card)
    }

    override suspend fun removeFavouriteCard(card: Card) {
        localDataSource.removeFavouriteCard(card)
    }

    override suspend fun refresh() {
        updateCardsFromRemoteDataSource()
    }

    private suspend fun updateCardsFromRemoteDataSource() {
        val remoteCardsResult = remoteDataSource.getCards()

        if (remoteCardsResult.isSuccess) {
            appSettings.isDataInitiallyLoaded = true
            localDataSource.saveCards(remoteCardsResult.getOrDefault(emptyList()))
        }
    }

}
