package laurens.hearthstoneassessment.activities.mainpage.grid

import android.arch.paging.PagedListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import laurens.hearthstoneassessment.R
import laurens.hearthstoneassessment.model.CardModel

class CardAdapter(private val onClickListener: (Int) -> Unit) : PagedListAdapter<CardModel, CardViewHolder>(
    DiffCallback()
) {

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cardModel = getItem(position)
        holder.view.setOnClickListener { onClickListener.also { it(position) } }
        when {
            cardModel != null -> holder.bindTo(cardModel)
            else -> holder.clear()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        val cardViewHolder = CardViewHolder(view)
        cardViewHolder.clear()
        return cardViewHolder
    }
}