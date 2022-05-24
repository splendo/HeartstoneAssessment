package com.kapanen.hearthstoneassessment.ui.home.tab

import com.kapanen.hearthstoneassessment.delegate.AdapterDelegatesManager
import com.kapanen.hearthstoneassessment.delegate.DelegateAdapter

class CardsListAdapter(delegatesManager: AdapterDelegatesManager) :
    DelegateAdapter<Any>(delegatesManager) {

    private var items: List<Any> = emptyList()

    fun setItems(items: List<Any>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItem(position: Int) = items[position]

    override fun getItemCount() = items.size

}
