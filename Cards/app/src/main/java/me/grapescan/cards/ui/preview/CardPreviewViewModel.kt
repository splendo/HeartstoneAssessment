package me.grapescan.cards.ui.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.grapescan.cards.data.Card
import me.grapescan.cards.data.CardRepository

class CardPreviewViewModel(
    initialCard: Card,
    private val repository: CardRepository
) : ViewModel() {

    val currentCard by lazy { MutableLiveData<Card>() }

    val cards: MutableLiveData<List<Card>> by lazy {
        MutableLiveData<List<Card>>().also { refresh(initialCard.id) }
    }

    private fun refresh(selectedCardId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrentSelection().let { data ->
                cards.postValue(data)
                data.find { it.id == selectedCardId }?.let {
                    onCardSwitch(it)
                }
            }
        }
    }

    fun setFavorite(cardId: String, checked: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        repository.setFavorite(cardId, checked)
        refresh(cardId)
    }

    fun onCardSwitch(item: Card) = currentCard.postValue(item)
}
