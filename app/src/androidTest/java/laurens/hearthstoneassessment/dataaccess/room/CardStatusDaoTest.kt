package laurens.hearthstoneassessment.dataaccess.room

import kotlinx.coroutines.runBlocking
import laurens.hearthstoneassessment.dataaccess.await
import laurens.hearthstoneassessment.dataaccess.dao.CardStatusDao
import laurens.hearthstoneassessment.model.CardStatus
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CardStatusDaoTest : RoomTest() {

    private lateinit var subject: CardStatusDao

    @Before
    fun setup() {
        subject = db.cardStatusDao()
    }

    @Test
    fun shouldToggleStatus() {
        runBlocking {
            Assert.assertEquals(null, subject.getCardStatusLive("card").await()?.favorite)
            subject.updateOrInsert(CardStatus("non_existent", true))
            Assert.assertEquals(true, subject.getCardStatusLive("non_existent").await()?.favorite)
            subject.updateOrInsert(CardStatus("non_existent", false))
            Assert.assertEquals(false, subject.getCardStatusLive("non_existent").await()?.favorite)

        }
    }

}