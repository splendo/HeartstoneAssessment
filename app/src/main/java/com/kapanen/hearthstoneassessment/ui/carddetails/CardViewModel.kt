package com.kapanen.hearthstoneassessment.ui.carddetails

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.model.CardImageItem
import com.kapanen.hearthstoneassessment.model.CardIntStatItem
import com.kapanen.hearthstoneassessment.model.CardStringStatItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MECHANICS_ITEM_DELIMITER = ", "

@HiltViewModel
class CardViewModel @Inject constructor(private val dispatcher: CoroutineDispatcher) : ViewModel() {

    private val _itemsLiveData = MutableLiveData<List<Any>>()
    val itemsLiveData: LiveData<List<Any>> = _itemsLiveData

    fun init(card: Card, resources: Resources) {
        viewModelScope.launch(dispatcher) {
            val items = mutableListOf<Any>()
            items.add(CardImageItem(card))
            items.addStringItem(R.string.card_stat_card_set, card.cardSet, resources)
            items.addStringItem(R.string.card_stat_type, card.type, resources)
            items.addStringItem(R.string.card_stat_faction, card.faction, resources)
            items.addStringItem(R.string.card_stat_rarity, card.rarity, resources)
            items.addIntItem(R.string.card_stat_cost, card.cost, resources)
            items.addIntItem(R.string.card_stat_attack, card.attack, resources)
            items.addIntItem(R.string.card_stat_health, card.health, resources)
            items.addStringItem(
                R.string.card_stat_text,
                HtmlCompat.fromHtml(card.htmlText.orEmpty(), HtmlCompat.FROM_HTML_MODE_LEGACY)
                    .toString(),
                resources
            )
            items.addStringItem(R.string.card_stat_flavor, card.flavor, resources)
            items.addStringItem(
                R.string.card_stat_collectible,
                card.collectible?.toString(),
                resources
            )
            items.addStringItem(R.string.card_stat_elite, card.elite?.toString(), resources)
            items.addStringItem(R.string.card_stat_player_class, card.playerClass, resources)
            items.addStringItem(
                R.string.card_stat_multi_class_group,
                card.multiClassGroup,
                resources
            )
            items.addStringItem(
                R.string.card_stat_mechanics,
                card.mechanics?.joinToString { MECHANICS_ITEM_DELIMITER },
                resources
            )
            _itemsLiveData.postValue(items.toList())
        }
    }

    private fun MutableList<Any>.addStringItem(
        @StringRes labelRes: Int,
        value: String?,
        resources: Resources
    ) {
        value?.let { add(CardStringStatItem(resources.getString(labelRes), it)) }
    }

    private fun MutableList<Any>.addIntItem(
        @StringRes labelRes: Int,
        value: Int?,
        resources: Resources
    ) {
        value?.let { add(CardIntStatItem(resources.getString(labelRes), it)) }
    }

}
