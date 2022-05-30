package com.kapanen.hearthstoneassessment.ui.filtering

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapanen.hearthstoneassessment.model.FilterHeader
import com.kapanen.hearthstoneassessment.model.FilterItem
import com.kapanen.hearthstoneassessment.model.FilterType
import com.kapanen.hearthstoneassessment.setting.AppSettings
import com.kapanen.hearthstoneassessment.util.toStringList
import com.kapanen.hearthstoneassessment.util.toStringSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FilteringViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val appSettings: AppSettings
) : ViewModel() {

    private val _items = MutableLiveData<List<Any>>().apply {
        value = emptyList<List<Any>>()
    }
    val items: LiveData<List<Any>> = _items

    fun prepareItems(resources: Resources) {
        viewModelScope.launch(dispatcher) {
            val itemList = mutableListOf<Any>()
            itemList.addFilterSection(
                fullFiltersListStr = appSettings.types,
                filtersStr = appSettings.typeFilter,
                filterType = FilterType.TYPE,
                resources = resources
            )
            itemList.addFilterSection(
                fullFiltersListStr = appSettings.rarities,
                filtersStr = appSettings.rarityFilter,
                filterType = FilterType.RARITY,
                resources = resources
            )
            itemList.addFilterSection(
                fullFiltersListStr = appSettings.classes,
                filtersStr = appSettings.classFilter,
                filterType = FilterType.CLASS,
                resources = resources
            )
            itemList.addFilterSection(
                fullFiltersListStr = appSettings.mechanics,
                filtersStr = appSettings.mechanicFilter,
                filterType = FilterType.MECHANIC,
                resources = resources
            )

            _items.postValue(itemList.toList())
        }
    }

    fun onPause() {
        appSettings.notifyFilteringUpdate()
    }

    private fun MutableList<Any>.addFilterSection(
        fullFiltersListStr: String,
        filtersStr: String,
        filterType: FilterType,
        resources: Resources
    ) {
        val filters = filtersStr.toStringList()
        val filterItems = mutableListOf<FilterItem>()
        fullFiltersListStr
            .toStringSet()
            .sortedWith { left, right ->
                when {
                    left != FilterItem.UNDEFINED && right != FilterItem.UNDEFINED -> {
                        left.compareTo(right)
                    }
                    left == FilterItem.UNDEFINED && right != FilterItem.UNDEFINED -> 1
                    left != FilterItem.UNDEFINED && right == FilterItem.UNDEFINED -> -1
                    else -> 0
                }
            }.forEach { item ->
                filterItems.add(
                    FilterItem(
                        label = item.replaceFirstChar { firstChar ->
                            if (firstChar.isLowerCase()) {
                                firstChar.titlecase(Locale.getDefault())
                            } else {
                                firstChar.toString()
                            }
                        },
                        value = item,
                        filterType = filterType,
                        isEnabled = filters.contains(item)
                    )
                )
            }

        if (filterItems.isNotEmpty()) {
            add(FilterHeader(resources.getString(filterType.filterLabelRes)))
            addAll(filterItems)
        }
    }

}
