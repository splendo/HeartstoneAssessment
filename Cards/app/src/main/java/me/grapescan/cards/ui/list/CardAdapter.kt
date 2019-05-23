package me.grapescan.cards.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import me.grapescan.cards.data.Card

class CardAdapter(
    var onItemClick: (item: Card) -> Unit = {}
) : ListAdapter<Card, CardViewHolder>(DIFF_CALLBACK) {

    companion object {
        @JvmStatic
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Card>() {

            override fun areItemsTheSame(left: Card, right: Card) = left == right

            override fun areContentsTheSame(left: Card, right: Card) = left == right
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CardViewHolder(parent, onItemClick)

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) = holder.bind(getItem(position))
}
