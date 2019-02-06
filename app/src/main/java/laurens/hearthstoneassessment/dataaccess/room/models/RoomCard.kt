package laurens.hearthstoneassessment.dataaccess.room.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import laurens.hearthstoneassessment.model.Card

class RoomCard {
    @Embedded
    lateinit var card: Card
    @Relation(parentColumn = "cardId", entityColumn = "cardId")
    lateinit var mechanics: List<CardMechanic>
    @Relation(parentColumn = "cardId", entityColumn = "cardId")
    lateinit var classes: List<CardClass>
}
