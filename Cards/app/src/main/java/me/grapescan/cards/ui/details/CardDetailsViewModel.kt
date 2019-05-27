package me.grapescan.cards.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.grapescan.cards.data.Card
import me.grapescan.cards.data.CardRepository

class CardDetailsViewModel(
    private val cardId: String,
    private val repository: CardRepository
) : ViewModel() {

    val card: MutableLiveData<Card> by lazy {
        MutableLiveData<Card>().also { refresh() }
    }

    fun setFavorite(cardId: String, checked: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        repository.setFavorite(cardId, checked)
        refresh()
    }

    private fun refresh() = viewModelScope.launch(Dispatchers.IO) {
        card.postValue(repository.getCard(cardId))
    }
}
