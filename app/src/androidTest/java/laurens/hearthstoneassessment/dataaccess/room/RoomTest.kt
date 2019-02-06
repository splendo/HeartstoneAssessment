package laurens.hearthstoneassessment.dataaccess.room

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import laurens.hearthstoneassessment.test.R
import org.junit.After
import org.junit.Before
import java.io.IOException


open class RoomTest {
    lateinit var db: LocalCardDatabase
        private set


    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getContext()
        val populateRoomCallback = PopulateRoomCallback(context.resources.openRawResource(R.raw.test_cards))
        db = Room.inMemoryDatabaseBuilder(
            context, LocalCardDatabase::class.java
        ).build()
        populateRoomCallback.database = db
        runBlocking {
            populateRoomCallback.insertCards()
        }
    }
}