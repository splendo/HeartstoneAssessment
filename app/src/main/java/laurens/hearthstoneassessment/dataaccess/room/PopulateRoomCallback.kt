package laurens.hearthstoneassessment.dataaccess.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.RoomDatabase
import kotlinx.coroutines.*
import laurens.hearthstoneassessment.dataaccess.JsonCardSequence
import org.jetbrains.anko.*
import java.io.InputStream
import kotlin.coroutines.resume

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
