package me.grapescan.cards.data.storage

import me.grapescan.cards.data.Card
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class CardMapperTest {

    companion object {

        private val fullCardDao = CardDao(
            "card-id",
            "Super Hero",
            "Magic Creatures",
            "Hero",
            "Common",
            20,
            3,
            100,
            "Very healthy hero.",
            "Nothing special, just OK.",
            "Alexander Pushkin",
            true,
            "Mage",
            "Kill 10 minions.",
            "https://www.gstatic.com/android/market_images/web/play_prism_hlock_2x.png",
            "https://www.gstatic.com/android/market_images/web/play_prism_hlock_2x.png",
            "enUS",
            listOf(CardDao.MechanicsDao("Deathrattle"), CardDao.MechanicsDao("Taunt"))
        )

        private val fullCard = Card(
            "card-id",
            "Super Hero",
            "Magic Creatures",
            "Hero",
            "Common",
            20,
            3,
            100,
            "Very healthy hero.",
            "Nothing special, just OK.",
            "Alexander Pushkin",
            true,
            "Mage",
            "Kill 10 minions.",
            "https://www.gstatic.com/android/market_images/web/play_prism_hlock_2x.png",
            "https://www.gstatic.com/android/market_images/web/play_prism_hlock_2x.png",
            listOf("Deathrattle", "Taunt")
        )

        private val partialCardDao = CardDao(
            "card-id",
            null,
            null,
            null,
            null,
            20,
            3,
            100,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

        private val partialCard = Card(
            "card-id",
            "Not specified",
            "Not specified",
            "Not specified",
            "Not specified",
            20,
            3,
            100,
            "",
            "Not specified",
            "Not specified",
            false,
            "Not specified",
            "Not specified",
            "",
            "",
            emptyList(),
            false
        )
    }

    private lateinit var target: CardMapper

    @Before
    fun setup() {
        target = CardMapper()
    }

    @Test
    fun testFullyFilledCardMapping() {
        target.map(fullCardDao) shouldEqual fullCard
    }

    @Test
    fun testPartiallyFilledCardMapping() {
        target.map(partialCardDao) shouldEqual partialCard
    }
}
