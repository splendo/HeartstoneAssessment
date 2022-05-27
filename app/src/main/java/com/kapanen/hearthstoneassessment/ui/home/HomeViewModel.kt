package com.kapanen.hearthstoneassessment.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.di.AppModule
import com.kapanen.hearthstoneassessment.model.CardsTab
import com.kapanen.hearthstoneassessment.setting.AppSettings
import com.kapanen.hearthstoneassessment.util.toItemsString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cardsRepository: CardsRepository,
    @AppModule.HomeCardTabs
    private var cardTabs: List<CardsTab>,
    private val dispatcher: CoroutineDispatcher,
    private val appSettings: AppSettings
) : ViewModel() {

    fun isAscendingSorting() = appSettings.isAscendingSorting

    fun switchSortingOrder() {
        appSettings.setSorting(!appSettings.isAscendingSorting)
    }

    fun getCardTabs() = cardTabs
    fun getCardTabsLabelRes(position: Int) =
        cardTabs.getOrNull(position)?.cardType?.label ?: R.string.card_type_favourites

    fun loadCards() {
        viewModelScope.launch(dispatcher) {
            val typeSet = mutableSetOf<String>()
            val raritySet = mutableSetOf<String>()
            val classSet = mutableSetOf<String>()
            val mechanicSet = mutableSetOf<String>()
            cardsRepository.getCards().getOrDefault(emptyList()).forEach { card ->
                typeSet.addItem(card.type)
                raritySet.addItem(card.rarity)
                classSet.addItem(card.playerClass)
                card.mechanics?.forEach { mechanicSet.addItem(it.name) }
            }
            appSettings.types = typeSet.toItemsString()
            appSettings.rarities = raritySet.toItemsString()
            appSettings.classes = classSet.toItemsString()
            appSettings.mechanics = mechanicSet.toItemsString()
        }
    }

    private fun MutableSet<String>.addItem(item: String?) {
        item?.let { this.add(it) }
    }

}
