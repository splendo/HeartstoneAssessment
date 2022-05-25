package com.kapanen.hearthstoneassessment.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kapanen.hearthstoneassessment.api.CardsApiService
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.util.toList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.Exception

class DefaultRemoteCardsDataSource(
    private val cardsApiService: CardsApiService,
    private val ioDispatcher: CoroutineDispatcher
) :
    RemoteCardsDataSource {

    private val observableCards = MutableLiveData<Result<List<Card>>>()

    override fun observeCards(): LiveData<Result<List<Card>>> = observableCards

    override suspend fun getCards(): Result<List<Card>> {
        return observableCards.value ?: loadCards()
    }


    override suspend fun refresh() {
        loadCards()
    }

    private suspend fun loadCards(): Result<List<Card>> {
        val result = withContext(ioDispatcher) {
            if (cardsApiService.cards().isSuccessful) {
                Result.success(cardsApiService.cards().body()?.toList() ?: emptyList())
            } else {
                Result.failure(
                    Exception(
                        cardsApiService.cards().errorBody()?.toString() ?: "Couldn't load cards"
                    )
                )
            }
        }
        return result.also { observableCards.postValue(it) }
    }

}
