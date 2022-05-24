package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.databinding.LoadingItemBinding
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate
import com.kapanen.hearthstoneassessment.model.LoadingItem

class LoadingItemDelegate :
    SimpleDelegate<LoadingItem, LoadingItemDelegate.ViewHolder>(R.layout.loading_item) {

    override fun suitFor(position: Int, data: Any) = data is LoadingItem

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        LoadingItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, data: LoadingItem) = Unit

    class ViewHolder(binding: LoadingItemBinding) : RecyclerView.ViewHolder(binding.root)
}
