package com.kapanen.hearthstoneassessment.ui.delegate

import androidx.recyclerview.widget.DiffUtil
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.model.LoadingItem
import com.kapanen.hearthstoneassessment.model.NoDataItem

class DiffCallback(
    private val oldItems: List<Any>,
    private val newItems: List<Any>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return areItemsContentTheSame(oldItem, newItem)
    }

    private fun areItemsContentTheSame(oldItem: Any, newItem: Any): Boolean {
        return (oldItem is LoadingItem && newItem is LoadingItem)
                || (oldItem is NoDataItem && newItem is NoDataItem)
                || compareCards(oldItem, newItem)
    }

    private fun compareCards(oldItem: Any, newItem: Any): Boolean {
        return oldItem is Card && newItem is Card && oldItem.cardId == newItem.cardId
    }

}
