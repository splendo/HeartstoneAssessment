package com.kapanen.hearthstoneassessment.delegate

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class DelegateAdapter<T : Any>(private val delegatesManager: AdapterDelegatesManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.getDelegateFor(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.getDelegateFor(holder.itemViewType)
            .onBindViewHolder(holder, getItem(position))
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        delegatesManager.getDelegateFor(holder.itemViewType).onViewRecycled(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getViewTypeFor(position, getItem(position))
    }

    abstract fun getItem(position: Int): T

}
