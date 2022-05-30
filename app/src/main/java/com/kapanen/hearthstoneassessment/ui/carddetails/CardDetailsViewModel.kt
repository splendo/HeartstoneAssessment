package com.kapanen.hearthstoneassessment.ui.carddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.setting.AppSettings
import com.kapanen.hearthstoneassessment.util.sort
import com.kapanen.hearthstoneassessment.util.filter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardDetailsViewModel @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val dispatcher: CoroutineDispatcher,
    private val appSettings: AppSettings
) : ViewModel() {

    private val _cardsLiveData = MutableLiveData<List<Card>>()
    val cardsLiveData: LiveData<List<Card>> = _cardsLiveData

    fun init(cardId: String, cardType: String?, isFavoriteFeed: Boolean) {
        viewModelScope.launch(dispatcher) {
            val res = loadCards(cardId, cardType, isFavoriteFeed)
            _cardsLiveData.postValue(res)
        }
    }

    private suspend fun loadCards(
        cardId: String,
        cardType: String?,
        isFavoriteFeed: Boolean
    ): List<Card> {
        val result = when {
            !isFavoriteFeed && cardType != null -> {
                cardsRepository.getCards(cardType)
            }
            !isFavoriteFeed -> {
                cardsRepository.getCard(cardId).map { listOf(it) }
            }
            else -> cardsRepository.getFavouriteCards()
        }
        return result.getOrDefault(emptyList()).sort(appSettings).filter(appSettings)
    }

}
