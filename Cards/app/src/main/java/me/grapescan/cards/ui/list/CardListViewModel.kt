package me.grapescan.cards.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.grapescan.cards.data.Card
import me.grapescan.cards.data.CardRepository

class CardListViewModel(
    repository: CardRepository
) : ViewModel() {
    val cards: MutableLiveData<List<Card>> by lazy {
        MutableLiveData<List<Card>>().also {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getCards().let {
                    repository.setCurrentSelection(it)
                    cards.postValue(it)
                }
            }
        }
    }
}
