package com.kapanen.hearthstoneassessment.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kapanen.hearthstoneassessment.model.Card

class AppSettings constructor(storage: Storage) : StoragePropertyDelegate(storage) {

    private val _favoriteUpdates = MutableLiveData<Card>()
    val favoriteUpdates: LiveData<Card> = _favoriteUpdates

    private val _sortingUpdates = MutableLiveData<Boolean>()
    val sortingUpdates: LiveData<Boolean> = _sortingUpdates

    private val _filteringUpdates = MutableLiveData<Long>()
    val filteringUpdates: LiveData<Long> = _filteringUpdates

    var isDataInitiallyLoaded: Boolean by default(false)
    var isAscendingSorting: Boolean by default(true)
        private set

    var types: String by default("")
    var rarities: String by default("")
    var classes: String by default("")
    var mechanics: String by default("")

    var typyFilter: String by default("")
    var rarityFilter: String by default("")
    var classFilter: String by default("")
    var mechanicFilter: String by default("")

    fun notifyFavoriteUpdate(card: Card) {
        _favoriteUpdates.postValue(card)
    }

    fun setSorting(isAscendingSorting: Boolean) {
        this.isAscendingSorting = isAscendingSorting
        _sortingUpdates.postValue(isAscendingSorting)
    }

    fun notifyFilteringUpdate() {
        _filteringUpdates.postValue(System.currentTimeMillis())
    }

}
