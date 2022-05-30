package com.kapanen.hearthstoneassessment.ui.home.tab

import androidx.lifecycle.*
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.model.CardWrapper
import com.kapanen.hearthstoneassessment.model.CardsTab
import com.kapanen.hearthstoneassessment.model.LoadingItem
import com.kapanen.hearthstoneassessment.setting.AppSettings
import com.kapanen.hearthstoneassessment.util.sort
import com.kapanen.hearthstoneassessment.util.filter
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
                    val resultCards: List<Card> = if (isFavorite) {
                        newCards.toList().sort(appSettings)
                    } else {
                        newCards.toList()
                    }
                    cards = resultCards
                    val filteredCards = resultCards.filter(appSettings).toList()
                    _items.postValue(
                        if (!isFavorite && currentItemsCount < filteredCards.size) {
                            filteredCards.subList(0, currentItemsCount).wrap()
                        } else {
                            filteredCards.wrap()
                        }
                    )
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
            cards = cards.sort(appSettings)
            _items.postValue(cards.filter(appSettings).take(PAGE_SIZE).wrap())
        }
    }

    fun loadNextPage(currentItemsCount: Int) {
        viewModelScope.launch(dispatcher) {
            val requestedPosition = currentItemsCount + PAGE_SIZE
            cards.let { cardList ->
                val filteredCards = cardList.filter(appSettings)
                _items.postValue(
                    if (requestedPosition < filteredCards.size) {
                        filteredCards
                            .subList(0, currentItemsCount + PAGE_SIZE).wrap() + LoadingItem()
                    } else {
                        filteredCards.wrap()
                    }
                )
            }
        }
    }

    fun updateSorting() {
        viewModelScope.launch(dispatcher) {
            cards = cards.sort(appSettings)
            loadNextPage(0)
        }
    }

    fun updateFiltering() {
        viewModelScope.launch(dispatcher) {
            loadNextPage(0)
        }
    }

    private fun List<Card>.wrap() = this.map { CardWrapper(it, isFavorite) }

}
