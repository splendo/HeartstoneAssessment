package me.grapescan.cards.ui.list

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.target.DrawableImageViewTarget
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.partial_card_placeholder.view.*
import me.grapescan.cards.R
import me.grapescan.cards.data.Card
import me.grapescan.cards.ext.inflate
import me.grapescan.cards.ui.glide.GlideApp

class CardViewHolder(
    parent: ViewGroup,
    val onClickListener: (view: View, item: Card) -> Unit
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_card)) {

    val content: ImageView = itemView.content
    val placeholder = itemView.placeholder
    val placeholderName = itemView.placeholderName
    val placeholderText = itemView.placeholderText

    fun bind(item: Card) {
        content.setImageBitmap(null)
        placeholder.isVisible = false
        placeholderName.text = item.name
        placeholderText.text = item.text
        itemView.setOnClickListener { onClickListener(itemView, item) }
        if (item.imageUrl.isNotEmpty()) {
            GlideApp.with(itemView.context)
                .load(item.imageUrl)
                .into(object : DrawableImageViewTarget(content) {
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        placeholder.isVisible = true
                    }
                })
        } else {
            placeholder.isVisible = true
        }
    }
}
