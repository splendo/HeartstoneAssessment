package laurens.hearthstoneassessment.dataaccess

import kotlinx.coroutines.runBlocking
import laurens.hearthstoneassessment.dataaccess.room.RoomCardDao
import laurens.hearthstoneassessment.dataaccess.room.RoomTest
import laurens.hearthstoneassessment.model.CardModel
import laurens.hearthstoneassessment.model.CardStatus
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.AssertionError
import javax.security.auth.Subject

class CardRepositoryTest : RoomTest() {

    private lateinit var subject: CardRepository
    private lateinit var cards: List<CardModel>

    @Before
    fun setup() {
        subject = CardRepository(RoomCardDao(db.cardDao()), db.cardStatusDao())
        runBlocking {
            cards = subject.cards().awaitFirstData()
        }
    }

    @Test
    fun shouldSetFavoriteStatus() {
        subject.selectItem(2)
        subject.setCurrentFavoriteStatus(true)
        runBlocking {
            val cardStatus = cards.first { it.card.cardId == "EX1_100" }
                .status.await()
            assertEquals(CardStatus("EX1_100", true), cardStatus)
        }
    }

    @Test
    fun shouldTraverse() {
        subject.selectItem(2)
        val traverser = subject.traverser ?: throw AssertionError("Traverser should not be null")
        assertEquals("EX1_100", traverser.current()?.card?.cardId)
        assertEquals("EX1_012", traverser.previous()?.card?.cardId)
        assertEquals("EX1_100", traverser.next()?.card?.cardId)
        assertEquals("EX1_556", traverser.next()?.card?.cardId)
    }


}