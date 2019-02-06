package laurens.hearthstoneassessment.dataaccess.room

import android.arch.paging.DataSource
import laurens.hearthstoneassessment.dataaccess.SortDirection
import laurens.hearthstoneassessment.dataaccess.dao.CardDao
import laurens.hearthstoneassessment.dataaccess.room.models.RoomCard
import laurens.hearthstoneassessment.model.Card
import org.jetbrains.anko.AnkoLogger

class RoomCardDao(private val cardDao: RawCardDao) :
    CardDao, AnkoLogger {
    override fun insert(card: Card) {
        cardDao.insert(card)
    }

    override fun loadCards(
        type: String?,
        cardClass: String?,
        mechanic: String?,
        rarity: String?,
        sortDirection: SortDirection
    ): DataSource.Factory<Int, Card> {
        val query = QueryCreator.createQuery(type, cardClass, mechanic, rarity, sortDirection)
        return cardDao.loadCards(query).map {
            convertRoomCardToCard(it)
        }
    }

    private fun convertRoomCardToCard(it: RoomCard): Card {
        return it.card.copy(
            classes = it.classes.map { it.value },
            mechanics = it.mechanics.map { it.mechanic }
        )
    }
}