package com.kapanen.hearthstoneassessment.ui.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kapanen.hearthstoneassessment.R
import com.kapanen.hearthstoneassessment.data.CardsRepository
import com.kapanen.hearthstoneassessment.databinding.FavouriteItemBinding
import com.kapanen.hearthstoneassessment.delegate.SimpleDelegate
import com.kapanen.hearthstoneassessment.model.FavouriteItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavouriteItemDelegate(
    private val cardsRepository: CardsRepository,
    private val dispatcher: CoroutineDispatcher
) :
    SimpleDelegate<FavouriteItem, FavouriteItemDelegate.ViewHolder>(R.layout.favourite_item),
    CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = dispatcher

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        FavouriteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, data: FavouriteItem) {
        holder.binding.apply {
            favouriteItemIcon.setBackgroundResource(
                if (data.card.isFavorite) {
                    R.drawable.ic_filled_favourite
                } else {
                    R.drawable.ic_favourite
                }
            )
            favouriteItemIcon.setOnClickListener {
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

    override fun suitFor(position: Int, data: Any) = data is FavouriteItem

    class ViewHolder(val binding: FavouriteItemBinding) : RecyclerView.ViewHolder(binding.root)

}
