package laurens.hearthstoneassessment.dataaccess.room.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import laurens.hearthstoneassessment.model.Card
import laurens.hearthstoneassessment.model.Mechanic

@Entity(
    tableName = "card_mechanic", primaryKeys = ["cardId", "name"], foreignKeys = [
        ForeignKey(
            entity = Card::class,
            parentColumns = ["cardId"],
            childColumns = ["cardId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CardMechanic(
    val cardId: String,
    @Embedded
    val mechanic: Mechanic
)
