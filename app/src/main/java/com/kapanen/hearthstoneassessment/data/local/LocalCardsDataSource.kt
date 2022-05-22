package com.kapanen.hearthstoneassessment.data.local

import androidx.lifecycle.LiveData
import com.kapanen.hearthstoneassessment.model.Card

interface LocalCardsDataSource {

    fun observeCards(): LiveData<Result<List<Card>>>

    fun observeCards(cardType: String): LiveData<Result<List<Card>>>

    fun observeFavouriteCards(): LiveData<Result<List<Card>>>

    suspend fun getCards(): Result<List<Card>>

    suspend fun getCards(cardType: String): Result<List<Card>>

    suspend fun getFavouriteCards(): Result<List<Card>>

    suspend fun addFavouriteCard(card: Card)

    suspend fun removeFavouriteCard(card: Card)

    suspend fun saveCards(cards: List<Card>)

}
