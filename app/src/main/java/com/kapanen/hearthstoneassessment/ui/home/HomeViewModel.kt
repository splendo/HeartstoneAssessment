package com.kapanen.hearthstoneassessment.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.data.CardType
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.di.AppModule
import com.kapanen.hearthstoneassessment.model.CardsTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cardsRepository: CardsRepository,
    @AppModule.HomeCardTabs
    private var cardTabs: List<CardsTab>
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    init {
        loadCards()
    }

    fun getCardTabs() = cardTabs
    fun getCardTabsLabelRes(position: Int) =
        cardTabs.getOrNull(position)?.cardType?.label ?: R.string.card_type_favourites

    private fun loadCards() {
        viewModelScope.launch {
            cardsRepository.getCards()
        }
    }

}
