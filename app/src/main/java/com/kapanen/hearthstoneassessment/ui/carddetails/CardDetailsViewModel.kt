package com.kapanen.hearthstoneassessment.ui.carddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardDetailsViewModel @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _cardsLiveData = MutableLiveData<List<Card>>()
    val cardsLiveData: LiveData<List<Card>> = _cardsLiveData

    fun init(cardId: String, cardType: String?) {
        viewModelScope.launch(dispatcher) {
            val res = loadCards(cardId, cardType)
            _cardsLiveData.postValue(res)
        }
    }

    private suspend fun loadCards(cardId: String, cardType: String?): List<Card> {
        return cardType?.let {
            cardsRepository.getCards(cardType).getOrDefault(emptyList())
        } ?: run {
            cardsRepository.getCard(cardId).map { listOf(it) }.getOrDefault(emptyList())
        }
    }

}
