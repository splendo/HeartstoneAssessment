package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.databinding.FilteringHeaderBinding
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate
import com.kapanen.hearthstoneassessment.model.FilterHeader

class FilteringHeaderDelegate :
    SimpleDelegate<FilterHeader, FilteringHeaderDelegate.ViewHolder>(R.layout.filtering_header) {

    override fun suitFor(position: Int, data: Any) = data is FilterHeader

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        FilteringHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, data: FilterHeader) {
        holder.binding.filteringHeaderText.text = data.title
    }

    class ViewHolder(val binding: FilteringHeaderBinding) : RecyclerView.ViewHolder(binding.root)
}
