package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.databinding.CardItemBinding
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate
import com.kapanen.hearthstoneassessment.model.CardWrapper
import com.kapanen.hearthstoneassessment.ui.home.HomeFragmentDirections
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CardDelegate(
    private val cardsRepository: CardsRepository,
    private val dispatcher: CoroutineDispatcher
) :
    SimpleDelegate<CardWrapper, CardDelegate.ViewHolder>(R.layout.card_item), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = dispatcher

    override fun suitFor(position: Int, data: Any) = data is CardWrapper
    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        CardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, data: CardWrapper) {
        val card = data.card
        holder.binding.apply {
            cardItemTitle.text = card.name
            cardItemImage.setImageURI(card.img)
            cardItemText.text =
                HtmlCompat.fromHtml(card.htmlText.orEmpty(), HtmlCompat.FROM_HTML_MODE_LEGACY)
            cardItemFavouriteIcon.setBackgroundResource(
                if (card.isFavorite) {
                    R.drawable.ic_filled_favourite
                } else {
                    R.drawable.ic_favourite
                }
            )

            cardItemFavouriteIcon.setOnClickListener {
                launch(dispatcher) {
                    if (card.isFavorite) {
                        cardsRepository.removeFavouriteCard(card)
                    } else {
                        cardsRepository.addFavouriteCard(card)
                    }
                }
            }
            root.setOnClickListener {
                root.findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToNavigationCardDetails(
                        card.cardId,
                        card.cardType?.typeName,
                        data.isFavoriteFeed
                    )
                )
            }
        }
    }

    class ViewHolder(val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root)

}
