package com.kapanen.hearthstoneassessment.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.di.AppModule
import com.kapanen.hearthstoneassessment.model.CardsTab
import com.kapanen.hearthstoneassessment.setting.AppSettings
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
            if (!appSettings.isDataInitiallyLoaded) {
                cardsRepository.getCards()
            }
        }
    }

}
