package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.databinding.FilteringItemBinding
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate
import com.kapanen.hearthstoneassessment.model.FilterHeader
import com.kapanen.hearthstoneassessment.model.FilterItem
import com.kapanen.hearthstoneassessment.setting.AppSettings
import javax.inject.Inject

class FilteringItemDelegate @Inject constructor(private val appSettings: AppSettings) :
    SimpleDelegate<FilterItem, FilteringItemDelegate.ViewHolder>(R.layout.filtering_item) {

    override fun suitFor(position: Int, data: Any) = data is FilterHeader

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        FilteringItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, data: FilterItem) {
        holder.binding.filteringItemLabel.text = data.label
        holder.binding.filteringItemSwitch.isEnabled = data.isEnabled
        holder.binding.filteringItemSwitch.setOnCheckedChangeListener { _, isChecked ->

        }
    }

    class ViewHolder(val binding: FilteringItemBinding) : RecyclerView.ViewHolder(binding.root)
}
