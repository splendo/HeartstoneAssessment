package laurens.hearthstoneassessment.dataaccess.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import laurens.hearthstoneassessment.R
import laurens.hearthstoneassessment.dataaccess.dao.CardStatusDao
import laurens.hearthstoneassessment.dataaccess.room.models.CardClass
import laurens.hearthstoneassessment.dataaccess.room.models.CardMechanic
import laurens.hearthstoneassessment.model.Card
import laurens.hearthstoneassessment.model.CardStatus

@Database(
    entities = [Card::class, CardStatus::class, CardMechanic::class, CardClass::class],
    version = 1,
    exportSchema = false
)
abstract class LocalCardDatabase : RoomDatabase() {
    abstract fun cardDao(): RawCardDao
    abstract fun cardStatusDao(): CardStatusDao

    companion object {
        fun create(context: Context): LocalCardDatabase {
            val populateRoomCallback =
                PopulateRoomCallback(context.resources.openRawResource(R.raw.cards))
            val database = Room.databaseBuilder(context, LocalCardDatabase::class.java, "card_database")
                .addCallback(populateRoomCallback)
                .build()
            populateRoomCallback.database = database
            return database
        }
    }
}
