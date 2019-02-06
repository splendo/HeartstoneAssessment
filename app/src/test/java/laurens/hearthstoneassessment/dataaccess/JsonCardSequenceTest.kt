package laurens.hearthstoneassessment.dataaccess

import org.junit.Assert.*
import org.junit.Test

class JsonCardSequenceTest {
    private val jsonCardSequence = JsonCardSequence(javaClass.getResourceAsStream("cards.json")!!)
    val cards = jsonCardSequence.streamCards().toList()

    @Test
    fun shouldLoadCards() {
        assertEquals(3116, cards.size)
    }

    @Test
    fun shouldHaveIds() {
        cards.forEach {
            assertTrue(it.cardId.isNotBlank())
        }
    }

    @Test
    fun shouldLoadMechanics() {
        assertTrue(
            cards.any { it.mechanics.any { it.name.isNotBlank() } }
        )
    }

    @Test
    fun shouldLoadClasses() {
        assertTrue(
            cards.any { it.classes.any { it.isNotBlank() } }
        )
    }
}