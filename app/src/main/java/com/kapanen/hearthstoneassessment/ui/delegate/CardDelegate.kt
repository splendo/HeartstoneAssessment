package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.databinding.CardItemBinding
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate
import com.kapanen.hearthstoneassessment.model.Card
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
        holder.binding.cardItemTitle.text = data.name
        holder.binding.cardItemImage.setImageURI(data.img)
        holder.binding.cardItemText.text =
            HtmlCompat.fromHtml(data.htmlText.orEmpty(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        holder.binding.cardItemFavouriteIcon.isChecked = data.isFavorite
        holder.binding.cardItemFavouriteIcon.setOnCheckedChangeListener { _, isChecked ->
            launch(dispatcher) {
                if (isChecked) {
                    cardsRepository.addFavouriteCard(data)
                } else {
                    cardsRepository.removeFavouriteCard(data)
                }
            }
        }
    }

    class ViewHolder(val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root)

}
