package laurens.hearthstoneassessment.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "card")
data class Card
@JvmOverloads
constructor(
    @PrimaryKey val cardId: String,
    val name: String,
    val cardSet: String,
    val type: String,
    val locale: String,
    val text: String?,
    val rarity: String?,
    val cost: String?,
    val attack: String?,
    val health: String?,
    val flavor: String?,
    val faction: String?,
    val durability: String?,
    val race: String?,
    val artist: String?,
    val multiClassGroup: String?,
    val playerClass: String?,
    val howToGet: String?,
    val howToGetGold: String?,
    val img: String?,
    val imgGold: String?,
    val collectible: Boolean?,
    val elite: Boolean?,
    @Ignore val classes: Collection<String> = emptyList(),
    @Ignore val mechanics: Collection<Mechanic> = emptyList()
)
