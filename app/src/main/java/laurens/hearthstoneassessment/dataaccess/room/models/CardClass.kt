package laurens.hearthstoneassessment.dataaccess.room.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import laurens.hearthstoneassessment.model.Card

@Entity(
    tableName = "card_class", primaryKeys = ["cardId", "value"], foreignKeys = [
        ForeignKey(
            entity = Card::class,
            parentColumns = ["cardId"],
            childColumns = ["cardId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CardClass(
    val cardId: String,
    val value: String
)
