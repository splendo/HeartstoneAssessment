package me.grapescan.cards.ui.preview

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.partial_card_placeholder.view.*
import me.grapescan.cards.R
import me.grapescan.cards.data.Card
import me.grapescan.cards.ext.inflate
import me.grapescan.cards.ui.glide.GlideApp

class CardPreviewViewHolder(
    parent: ViewGroup,
    private val listener: CardPreviewAdapter.ContentLoadingListener
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_card_preview)) {

    val content = itemView.content
    val placeholder = itemView.placeholder
    val placeholderName = itemView.placeholderName
    val placeholderText = itemView.placeholderText

    fun bind(item: Card) {
        content.setImageBitmap(null)
        placeholder.isVisible = false
        placeholderName.text = item.name
        placeholderText.text = item.text
        if (item.imageUrl.isNotEmpty()) {
            GlideApp.with(content.context)
                .load(item.imageUrl)
                .into(object : DrawableImageViewTarget(content) {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        super.onResourceReady(resource, transition)
                        listener.onLoadingSuccess(item)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        placeholder.isVisible = true
                        listener.onLoadingError(item)
                    }
                })
        } else {
            placeholder.isVisible = true
            listener.onLoadingError(item)
        }
    }
}
