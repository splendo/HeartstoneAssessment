package laurens.hearthstoneassessment.activities.mainpage.grid

import android.support.v7.util.DiffUtil
import laurens.hearthstoneassessment.model.CardModel

class DiffCallback : DiffUtil.ItemCallback<CardModel>() {
    override fun areItemsTheSame(first: CardModel, second: CardModel): Boolean {
        return first.card.cardId == second.card.cardId
    }

    override fun areContentsTheSame(first: CardModel, second: CardModel): Boolean {
        return first.card == second.card &&
                first.status.value == second.status.value
    }
}