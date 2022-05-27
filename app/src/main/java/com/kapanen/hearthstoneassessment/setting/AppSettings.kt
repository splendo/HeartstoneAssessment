package com.kapanen.hearthstoneassessment.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kapanen.hearthstoneassessment.model.Card

class AppSettings constructor(storage: Storage) : StoragePropertyDelegate(storage) {

    private val _favoriteUpdates = MutableLiveData<Card>()
    val favoriteUpdates: LiveData<Card> = _favoriteUpdates

    var isDataInitiallyLoaded: Boolean by default(false)

    var types: String by default("")
    var rarities: String by default("")
    var classes: String by default("")
    var mechanics: String by default("")

    fun notifyFavoriteUpdate(card: Card) {
        _favoriteUpdates.postValue(card)
    }

}
