package laurens.hearthstoneassessment.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "card_status")
data class CardStatus(
    @PrimaryKey
    val cardId: String,
    val favorite: Boolean
)