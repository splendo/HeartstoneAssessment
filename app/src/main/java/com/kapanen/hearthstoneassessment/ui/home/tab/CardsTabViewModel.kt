package com.kapanen.hearthstoneassessment.ui.home.tab

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.model.CardsTab
import com.kapanen.hearthstoneassessment.model.LoadingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

private const val PAGE_SIZE = 20

@HiltViewModel
class CardsTabViewModel @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val dispatcher: CoroutineDispatcher
) :
    ViewModel() {

    private var cards: List<Card> = emptyList()

    fun loadCards(cardsTab: CardsTab): LiveData<List<Card>> {
        return liveData(dispatcher) {
            cardsTab.cardType?.let {
                cards =
                    cardsRepository.getCards(cardsTab.cardType.typeName).getOrDefault(emptyList())
            } ?: run {
                cards =
                    cardsRepository.getFavouriteCards().getOrDefault(emptyList()).take(PAGE_SIZE)
            }
            emit(cards.take(PAGE_SIZE))
        }
    }

    fun addNextPage(currentItemsCount: Int): List<Any> {
        val requestedPosition = currentItemsCount + PAGE_SIZE
        cards.let { cardList ->
            return if (requestedPosition < cardList.size) {
                cardList.subList(0, currentItemsCount + PAGE_SIZE) + LoadingItem()
            } else {
                cardList
            }
        }
    }

//    fun observeCards(cardsTab: CardsTab): LiveData<List<Card>> {
//        return cardsTab.cardType?.let {
//            cardsRepository.observeCards(cardsTab.cardType.typeName)
//                .map { it.getOrDefault(emptyList()) }
//        } ?: run {
//            cardsRepository.observeFavouriteCards().map { it.getOrDefault(emptyList()) }
//        }
//    }

}
