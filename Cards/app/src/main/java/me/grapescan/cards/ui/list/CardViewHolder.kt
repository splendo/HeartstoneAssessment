package me.grapescan.cards.ui.list

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_card.view.*
import me.grapescan.cards.R
import me.grapescan.cards.data.Card
import me.grapescan.cards.ext.inflate
import me.grapescan.cards.ui.glide.GlideApp

class CardViewHolder(
    parent: ViewGroup,
    val onClickListener: (item: Card) -> Unit
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_card)) {

    val content: ImageView = itemView.content

    fun bind(item: Card) {
        itemView.setOnClickListener { onClickListener(item) }
        GlideApp.with(itemView.context)
            .load(item.imageUrl)
            .into(content)
    }
}
