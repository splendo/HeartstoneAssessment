package com.kapanen.hearthstoneassessment.ui.home.tab

import androidx.lifecycle.*
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.model.CardsTab
import com.kapanen.hearthstoneassessment.model.LoadingItem
import com.kapanen.hearthstoneassessment.setting.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_SIZE = 20

@HiltViewModel
class CardsTabViewModel @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val dispatcher: CoroutineDispatcher,
    private val appSettings: AppSettings
) :
    ViewModel() {

    private val _items = MutableLiveData<List<Any>>().apply {
        value = emptyList<List<Any>>()
    }
    val items: LiveData<List<Any>> = _items

    private var cards: List<Card> = emptyList()
    private var isFavorite: Boolean = false

    fun onCardUpdate(card: Card, currentItemsCount: Int) {
        viewModelScope.launch(dispatcher) {
            cards.toMutableList().let { newCards ->
                var isUpdated = false
                cards.indexOfFirst { it.cardId == card.cardId }.takeIf { it >= 0 }
                    ?.let { index ->
                        if (!isFavorite) {
                            isUpdated = true
                            newCards[index] = card
                        }
                        if (isFavorite && !card.isFavorite) {
                            isUpdated = true
                            newCards.removeAt(index)
                        }
                    }
                if (isFavorite && card.isFavorite) {
                    isUpdated = true
                    newCards.add(card)
                }
                if (isUpdated) {
                    cards = if (!isFavorite && currentItemsCount < newCards.size) {
                        newCards.subList(0, currentItemsCount).toList()
                    } else {
                        newCards.toList()
                    }
                    _items.postValue(cards)
                }
            }

        }
    }

    fun loadCards(cardsTab: CardsTab) {
        viewModelScope.launch(dispatcher) {
            cardsTab.cardType?.let {
                isFavorite = false
                cards =
                    cardsRepository.getCards(cardsTab.cardType.typeName).getOrDefault(emptyList())
            } ?: run {
                isFavorite = true
                cards =
                    cardsRepository.getFavouriteCards().getOrDefault(emptyList()).take(PAGE_SIZE)
            }
            sort()
            _items.postValue(cards.take(PAGE_SIZE))
        }
    }

    fun loadNextPage(currentItemsCount: Int) {
        viewModelScope.launch(dispatcher) {
            val requestedPosition = currentItemsCount + PAGE_SIZE
            cards.let { cardList ->
                _items.postValue(
                    if (requestedPosition < cardList.size) {
                        cardList.subList(0, currentItemsCount + PAGE_SIZE) + LoadingItem()
                    } else {
                        cardList
                    }
                )
            }
        }
    }

    fun updateSorting() {
        viewModelScope.launch(dispatcher) {
            sort()
            loadNextPage(0)
        }
    }

    private fun sort() {
        cards = if (appSettings.isAscendingSorting) {
            cards.sortedBy { it.name }
        } else {
            cards.sortedByDescending { it.name }
        }
    }

}
