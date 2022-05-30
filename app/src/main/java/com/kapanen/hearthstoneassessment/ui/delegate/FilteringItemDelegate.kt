package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.databinding.FilteringItemBinding
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate
import com.kapanen.hearthstoneassessment.model.FilterItem
import com.kapanen.hearthstoneassessment.model.FilterType
import com.kapanen.hearthstoneassessment.setting.AppSettings
import com.kapanen.hearthstoneassessment.util.toItemsString
import com.kapanen.hearthstoneassessment.util.toStringSet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FilteringItemDelegate @Inject constructor(
    private val appSettings: AppSettings,
    private val coroutineDispatcher: CoroutineDispatcher
) :
    SimpleDelegate<FilterItem, FilteringItemDelegate.ViewHolder>(R.layout.filtering_item),
    CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = coroutineDispatcher

    override fun suitFor(position: Int, data: Any) = data is FilterItem

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        FilteringItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, data: FilterItem) {
        holder.setIsRecyclable(false)
        holder.binding.filteringItemLabel.text = data.label
        holder.binding.filteringItemSwitch.isChecked = data.isEnabled
        holder.binding.filteringItemSwitch.setOnCheckedChangeListener { _, isChecked ->
            launch(coroutineDispatcher) {
                if (isChecked) {
                    addFilterItem(data)
                } else {
                    removeFilterItem(data)
                }
                appSettings.notifyFilteringUpdate()
            }
        }
    }

    private fun addFilterItem(data: FilterItem) {
        when (data.filterType) {
            FilterType.TYPE -> {
                appSettings.typeFilter = addItemFromString(appSettings.typeFilter, data.value)
            }
            FilterType.RARITY -> {
                appSettings.rarityFilter = addItemFromString(appSettings.rarityFilter, data.value)
            }
            FilterType.CLASS -> {
                appSettings.classFilter = addItemFromString(appSettings.classFilter, data.value)
            }
            FilterType.MECHANIC -> {
                appSettings.mechanicFilter =
                    addItemFromString(appSettings.mechanicFilter, data.value)
            }
        }
    }

    private fun addItemFromString(filterStr: String, filterValue: String): String {
        val filterSet = filterStr.toStringSet().toMutableSet()
        filterSet.add(filterValue)
        return filterSet.filter { it.isNotBlank() }.toItemsString()
    }

    private fun removeFilterItem(data: FilterItem) {
        when (data.filterType) {
            FilterType.TYPE -> {
                appSettings.typeFilter = removeItemFromString(appSettings.typeFilter, data.value)
            }
            FilterType.RARITY -> {
                appSettings.rarityFilter =
                    removeItemFromString(appSettings.rarityFilter, data.value)
            }
            FilterType.CLASS -> {
                appSettings.classFilter = removeItemFromString(appSettings.classFilter, data.value)
            }
            FilterType.MECHANIC -> {
                appSettings.mechanicFilter =
                    removeItemFromString(appSettings.mechanicFilter, data.value)
            }
        }
    }

    private fun removeItemFromString(filterStr: String, filterValue: String): String {
        val filterSet = filterStr.toStringSet().toMutableSet()
        filterSet.remove(filterValue)
        return filterSet.filter { it.isNotBlank() }.toItemsString()
    }

    class ViewHolder(val binding: FilteringItemBinding) : RecyclerView.ViewHolder(binding.root)
}
