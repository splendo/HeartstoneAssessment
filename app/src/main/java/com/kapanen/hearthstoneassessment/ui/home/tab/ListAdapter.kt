package com.kapanen.hearthstoneassessment.ui.home.tab

import androidx.recyclerview.widget.DiffUtil
import com.kapanen.hearthstoneassessment.delegate.AdapterDelegatesManager
import com.kapanen.hearthstoneassessment.delegate.DelegateAdapter
import com.kapanen.hearthstoneassessment.ui.delegate.DiffCallback

class ListAdapter(delegatesManager: AdapterDelegatesManager) :
    DelegateAdapter<Any>(delegatesManager) {

    private var items: List<Any> = emptyList()

    fun setItems(items: List<Any>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(this.items, items))
        this.items = items
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItem(position: Int) = items[position]

    override fun getItemCount() = items.size

}
