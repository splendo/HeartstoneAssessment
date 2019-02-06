package laurens.hearthstoneassessment.dataaccess.room

import android.arch.paging.DataSource
import android.arch.persistence.db.SupportSQLiteQuery
import android.arch.persistence.room.*
import laurens.hearthstoneassessment.dataaccess.room.models.CardClass
import laurens.hearthstoneassessment.dataaccess.room.models.CardMechanic
import laurens.hearthstoneassessment.dataaccess.room.models.RoomCard
import laurens.hearthstoneassessment.model.Card

@Dao
interface RawCardDao {

    @RawQuery(observedEntities = [Card::class, CardMechanic::class, CardClass::class])
    fun loadCards(query: SupportSQLiteQuery): DataSource.Factory<Int, RoomCard>

    @Insert
    fun insert(card: Card)

    @Transaction
    fun insertCardWithJoins(card: Card) {
        insert(card)
        insertClasses(card.classes.map {
            CardClass(
                card.cardId,
                it
            )
        })
        insertMechanics(card.mechanics.map {
            CardMechanic(
                card.cardId,
                it
            )
        })
    }

    @Insert
    fun insertMechanics(cardMechanic: List<CardMechanic>)

    @Insert
    fun insertClasses(cardClass: List<CardClass>)
}
