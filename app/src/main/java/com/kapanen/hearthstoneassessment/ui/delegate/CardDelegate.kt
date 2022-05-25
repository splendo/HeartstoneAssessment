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
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.ui.home.HomeFragmentDirections
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CardDelegate(
    private val cardsRepository: CardsRepository,
    private val dispatcher: CoroutineDispatcher
) :
    SimpleDelegate<Card, CardDelegate.ViewHolder>(R.layout.card_item), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun suitFor(position: Int, data: Any) = data is Card
    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        CardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, data: Card) {
        holder.binding.apply {
            cardItemTitle.text = data.name
            cardItemImage.setImageURI(data.img)
            cardItemText.text =
                HtmlCompat.fromHtml(data.htmlText.orEmpty(), HtmlCompat.FROM_HTML_MODE_LEGACY)
            cardItemFavouriteIcon.setBackgroundResource(
                if (data.isFavorite) {
                    R.drawable.ic_filled_favourite
                } else {
                    R.drawable.ic_favourite
                }
            )

            cardItemFavouriteIcon.setOnClickListener {
                launch(dispatcher) {
                    if (data.isFavorite) {
                        cardsRepository.removeFavouriteCard(data)
                    } else {
                        cardsRepository.addFavouriteCard(data)
                    }
                }
            }
            root.setOnClickListener {
                root.findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToNavigationCardDetails(
                        data.cardId,
                        data.cardType?.typeName
                    )
                )
            }
        }
    }

    class ViewHolder(val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root)
}

