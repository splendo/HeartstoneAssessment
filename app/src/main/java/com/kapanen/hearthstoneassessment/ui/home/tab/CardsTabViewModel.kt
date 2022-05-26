package com.kapanen.hearthstoneassessment.ui.home.tab

import androidx.lifecycle.*
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.model.CardsTab
import com.kapanen.hearthstoneassessment.model.LoadingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_SIZE = 20

@HiltViewModel
class CardsTabViewModel @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val dispatcher: CoroutineDispatcher
) :
    ViewModel() {

    private val _items = MutableLiveData<List<Any>>().apply {
        value = emptyList<List<Any>>()
    }
    val items: LiveData<List<Any>> = _items

    private var cards: List<Card> = emptyList()

    fun loadCards(cardsTab: CardsTab) {
        viewModelScope.launch(dispatcher) {
            cardsTab.cardType?.let {
                cards =
                    cardsRepository.getCards(cardsTab.cardType.typeName).getOrDefault(emptyList())
            } ?: run {
                cards =
                    cardsRepository.getFavouriteCards().getOrDefault(emptyList()).take(PAGE_SIZE)
            }
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

}
