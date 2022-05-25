package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.databinding.CardImageItemBinding
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate
import com.kapanen.hearthstoneassessment.model.CardImageItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CardImageDelegate(
    private val cardsRepository: CardsRepository,
    private val dispatcher: CoroutineDispatcher
) :
    SimpleDelegate<CardImageItem, CardImageDelegate.ViewHolder>(R.layout.card_image_item),
    CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = dispatcher

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        CardImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, data: CardImageItem) {
        holder.binding.apply {
            cardImageItemTitle.text = data.card.name
            cardImageItemImage.setImageURI(data.card.img)
            cardImageItemFavouriteIcon.setBackgroundResource(
                if (data.card.isFavorite) {
                    R.drawable.ic_filled_favourite
                } else {
                    R.drawable.ic_favourite
                }
            )

            cardImageItemFavouriteIcon.setOnClickListener {
                cardImageItemFavouriteIcon.setBackgroundResource(
                    if (data.card.isFavorite) {
                        R.drawable.ic_favourite
                    } else {
                        R.drawable.ic_filled_favourite
                    }
                )
                launch(dispatcher) {
                    if (data.card.isFavorite) {
                        cardsRepository.removeFavouriteCard(data.card)
                    } else {
                        cardsRepository.addFavouriteCard(data.card)
                    }
                }
            }
        }
    }

    override fun suitFor(position: Int, data: Any) = data is CardImageItem

    class ViewHolder(val binding: CardImageItemBinding) : RecyclerView.ViewHolder(binding.root)


}
