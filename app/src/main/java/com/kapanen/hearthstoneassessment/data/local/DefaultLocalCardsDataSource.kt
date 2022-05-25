package com.kapanen.hearthstoneassessment.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.util.toCard
import com.kapanen.hearthstoneassessment.util.toCards
import com.kapanen.hearthstoneassessment.util.toDbCard

class DefaultLocalCardsDataSource(private val cardsDao: CardsDao) : LocalCardsDataSource {

    override fun observeCards(): LiveData<Result<List<Card>>> =
        cardsDao.observeDbCards().toCards()

    override fun observeCards(cardType: String): LiveData<Result<List<Card>>> =
        cardsDao.observeDbCardsByType(cardType).toCards()

    override fun observeCard(cardId: String): LiveData<Result<Card>> =
        cardsDao.observeDbCardById(cardId).map { dbCard -> Result.success(dbCard.toCard()) }

    override suspend fun getCards(): Result<List<Card>> = cardsDao.getDbCards().toCards()


    override suspend fun getCards(cardType: String): Result<List<Card>> =
        cardsDao.getDbCardByType(cardType).toCards()

    override suspend fun getCard(cardId: String): Result<Card> =
        Result.success(cardsDao.getDbCardById(cardId).toCard())

    override fun observeFavouriteCards(): LiveData<Result<List<Card>>> =
        cardsDao.observeFavouriteDbCards().toCards()

    override suspend fun getFavouriteCards(): Result<List<Card>> =
        cardsDao.getFavouriteDbCards().toCards()

    override suspend fun addFavouriteCard(card: Card) {
        cardsDao.updateDbCard(card.copy(isFavorite = true).toDbCard())
    }

    override suspend fun removeFavouriteCard(card: Card) {
        cardsDao.updateDbCard(card.copy(isFavorite = false).toDbCard())
    }

    override suspend fun saveCards(cards: List<Card>) {
        cardsDao.deleteDbCards()
        cards.forEach { card -> cardsDao.insertDbCard(card.toDbCard()) }
    }

}
