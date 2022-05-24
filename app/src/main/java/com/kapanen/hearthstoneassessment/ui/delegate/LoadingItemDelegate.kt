package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate
import com.kapanen.hearthstoneassessment.model.LoadingItem

class LoadingItemDelegate :
    SimpleDelegate<LoadingItem, LoadingItemDelegate.ViewHolder>(R.layout.loading_item) {

    override fun suitFor(position: Int, data: Any) = data is LoadingItem
    override fun onCreateViewHolder(parent: ViewGroup, view: View) = ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, data: LoadingItem) {
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
