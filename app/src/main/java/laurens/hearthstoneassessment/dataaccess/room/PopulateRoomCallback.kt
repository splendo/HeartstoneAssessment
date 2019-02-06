package laurens.hearthstoneassessment.dataaccess.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import laurens.hearthstoneassessment.dataaccess.JsonCardSequence
import org.jetbrains.anko.AnkoLogger
import java.io.InputStream

class PopulateRoomCallback(private val cards: InputStream) : RoomDatabase.Callback(), AnkoLogger {
    lateinit var database: LocalCardDatabase

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        GlobalScope.launch {
            insertCards()
        }
    }

    suspend fun insertCards() {
        withContext(Dispatchers.IO) {
            val cards = JsonCardSequence(cards).streamCards()
            cards.forEach {
                database.cardDao().insertCardWithJoins(it)
            }
        }
    }
}
