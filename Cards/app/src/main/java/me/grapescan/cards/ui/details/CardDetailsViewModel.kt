package me.grapescan.cards.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.grapescan.cards.data.Card
import me.grapescan.cards.data.CardRepository

class CardDetailsViewModel(
    private val initialCard: Card,
    private val repository: CardRepository
) : ViewModel() {

    val currentCard by lazy { MutableLiveData<Card>() }

    val cards: MutableLiveData<List<Card>> by lazy {
        MutableLiveData<List<Card>>().also { _ ->
            viewModelScope.launch(Dispatchers.IO) {
                val data = repository.getCurrentSelection()
                cards.postValue(data)
                data.find { it.id == initialCard.id }?.let {
                    onCardSwitch(it)
                }
            }
        }
    }

    fun setFavorite(cardId: String, checked: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        repository.setFavorite(cardId, checked)
        // TODO: refresh fav state
    }

    fun onCardSwitch(item: Card) = currentCard.postValue(item)
}
