package me.grapescan.cards.ui.preview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import me.grapescan.cards.data.Card

class CardPreviewAdapter(
    private val contentLoadingListener: ContentLoadingListener = object : ContentLoadingListener {}
) : ListAdapter<Card, CardPreviewViewHolder>(DIFF_CALLBACK) {

    companion object {
        @JvmStatic
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Card>() {

            override fun areItemsTheSame(left: Card, right: Card) = left.id == right.id

            override fun areContentsTheSame(left: Card, right: Card) = areItemsTheSame(left, right)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CardPreviewViewHolder(parent, contentLoadingListener)

    override fun onBindViewHolder(holder: CardPreviewViewHolder, position: Int) = holder.bind(getItem(position))

    interface ContentLoadingListener {
        fun onLoadingSuccess(card: Card) = Unit
        fun onLoadingError(card: Card) = Unit
    }
}
