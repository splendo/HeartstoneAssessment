package me.grapescan.cards.ui.details

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.item_card.view.*
import me.grapescan.cards.R
import me.grapescan.cards.data.Card
import me.grapescan.cards.ext.inflate
import me.grapescan.cards.ui.glide.GlideApp

class CardDetailsViewHolder(
    parent: ViewGroup,
    private val listener: CardDetailsAdapter.ContentLoadingListener
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_card_details)) {

    val content: ImageView = itemView.content

    fun bind(item: Card) {
        GlideApp.with(content.context)
            .load(item.imageUrl)
            .into(object : DrawableImageViewTarget(content) {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    super.onResourceReady(resource, transition)
                    listener.onLoadingSuccess(item)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    listener.onLoadingError(item)
                }
            })
    }
}
