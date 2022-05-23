package com.kapanen.hearthstoneassessment.ui.home.tab

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.model.CardsTab
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardsTabViewModel @Inject constructor(private val cardsRepository: CardsRepository) :
    ViewModel() {

    fun observeCards(cardsTab: CardsTab): LiveData<List<Card>> {
        return cardsTab.cardType?.let {
            cardsRepository.observeCards(cardsTab.cardType.typeName)
                .map { it.getOrDefault(emptyList()) }
        } ?: run {
            cardsRepository.observeFavouriteCards().map { it.getOrDefault(emptyList()) }
        }
    }

}
