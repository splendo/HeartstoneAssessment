package com.nielsmasdorp.heartstone.presentation.card.grid

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nielsmasdorp.heartstone.R
import com.nielsmasdorp.heartstone.presentation.card.CardViewModel
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.nielsmasdorp.heartstone.presentation.card.CardsActivity
import kotlinx.android.synthetic.main.grid_item_card.view.*

/**
 * Adapter that shows the list of cards used in the card grid
 */
class CardGridAdapter(fragment: Fragment) : RecyclerView.Adapter<CardGridAdapter.CardViewHolder>() {

    private var requestManager: RequestManager = Glide.with(fragment)

    private var viewHolderListener: ViewHolderListener = ViewHolderListenerImpl()

    var onCardClickedListener: ((CardViewModel) -> Unit)? = null

    var onClickedImageLoaded: (() -> Unit)? = null

    var cards: List<CardViewModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.grid_item_card, parent, false),
                requestManager,
                viewHolderListener
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) = holder.bind()

    override fun getItemCount(): Int = cards.size

    fun getPositionForViewModel(clickedViewModel: CardViewModel): Int {
        return cards.indexOf(clickedViewModel)
    }

    inner class ViewHolderListenerImpl : ViewHolderListener {

        override fun onLoadCompleted(view: ImageView, adapterPosition: Int) {
            if (CardsActivity.CURRENT_POSITION != adapterPosition) {
                return
            }
            onClickedImageLoaded?.invoke()
        }

        override fun onItemClicked(clickedPosition: Int) {
            onCardClickedListener?.invoke(cards[clickedPosition])
        }
    }

    inner class CardViewHolder(itemView: View, private val requestManager: RequestManager,
                               val viewHolderListener: ViewHolderListener) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            itemView.apply {
                cardGridImage.transitionName = cards[adapterPosition].name
                requestManager
                        .load(cards[adapterPosition].imgUrl)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(e: GlideException?, model: Any,
                                                      target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                                viewHolderListener.onLoadCompleted(cardGridImage, adapterPosition)
                                return false
                            }

                            override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                                viewHolderListener.onLoadCompleted(cardGridImage, adapterPosition)
                                return false
                            }
                        })
                        .into(cardGridImage)
                setOnClickListener { viewHolderListener.onItemClicked(adapterPosition) }
            }
        }
    }

    interface ViewHolderListener {

        fun onLoadCompleted(view: ImageView, adapterPosition: Int)

        fun onItemClicked(clickedPosition: Int)
    }
}