package me.grapescan.cards.ui.details

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import me.grapescan.cards.data.Card

class CardDetailsAdapter(
    private val contentLoadingListener: ContentLoadingListener = object : ContentLoadingListener {}
) : ListAdapter<Card, CardDetailsViewHolder>(DIFF_CALLBACK) {

    companion object {
        @JvmStatic
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Card>() {

            override fun areItemsTheSame(left: Card, right: Card) = left == right

            override fun areContentsTheSame(left: Card, right: Card) = left == right
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CardDetailsViewHolder(parent, contentLoadingListener)

    override fun onBindViewHolder(holder: CardDetailsViewHolder, position: Int) = holder.bind(getItem(position))

    interface ContentLoadingListener {
        fun onLoadingSuccess(card: Card) = Unit
        fun onLoadingError(card: Card) = Unit
    }
}
