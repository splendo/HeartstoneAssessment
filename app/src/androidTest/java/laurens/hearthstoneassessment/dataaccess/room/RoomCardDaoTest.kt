package laurens.hearthstoneassessment.dataaccess.room

import android.arch.paging.LivePagedListBuilder
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import laurens.hearthstoneassessment.dataaccess.SortDirection
import laurens.hearthstoneassessment.dataaccess.awaitFirstData
import laurens.hearthstoneassessment.model.Mechanic
import org.hamcrest.Matchers.containsInAnyOrder
import org.jetbrains.anko.AnkoLogger
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RoomCardDaoTest : RoomTest(), AnkoLogger {
    private lateinit var subject: RoomCardDao

    @Before
    fun setup() {
        subject = RoomCardDao(db.cardDao())
    }


    @Test
    fun shouldLoadAllTestCards() {
        runBlocking {
            val cards = subject.loadCards().awaitFirstData()
            assertEquals(5, cards.size)
        }
    }

    @Test
    fun shouldLoadClassesAndMechanicsInCard() {
        runBlocking {
            val card = subject.loadCards().awaitFirstData().first { it.cardId == "CFM_321" }
            Assert.assertThat(
                card.classes, containsInAnyOrder(
                    "Hunter",
                    "Paladin",
                    "Warrior"
                )
            )
            Assert.assertThat(
                card.mechanics, containsInAnyOrder(
                    Mechanic("Discover"),
                    Mechanic("Battlecry")
                )
            )
        }
    }

    @Test
    fun shouldSortCardsAscending() {
        runBlocking {
            val cards = subject.loadCards(sortDirection = SortDirection.ASCENDING).awaitFirstData()
            val cardNames = cards.map { it.name }
            assertEquals(cards.map { it.name }.sorted(), cardNames)
        }
    }

    @Test
    fun shouldSortCardsDescending() {
        runBlocking {
            val cards = subject.loadCards(sortDirection = SortDirection.DESCENDING).awaitFirstData()
            val cardNames = cards.map { it.name }
            assertEquals(cards.map { it.name }.sortedDescending(), cardNames)
        }
    }

    @Test
    fun shouldLoadLegendaryCards() {
        runBlocking {
            val cards = subject.loadCards(rarity = "Legendary").awaitFirstData()
            Assert.assertThat(
                cards.map { it.cardId }, containsInAnyOrder(
                    "EX1_012",
                    "EX1_100"
                )
            )
        }
    }

    @Test
    fun shouldLoadDeathrattleCards() {
        runBlocking {
            val cards = subject.loadCards(mechanic = "Deathrattle").awaitFirstData()
            Assert.assertThat(
                cards.map { it.cardId }, containsInAnyOrder(
                    "EX1_012",
                    "EX1_556"
                )
            )
        }
    }

    @Test
    fun shouldLoadClasses() {
        runBlocking {
            val cards = subject.loadCards(cardClass = "Hunter").awaitFirstData()
            Assert.assertThat(
                cards.map { it.cardId }, containsInAnyOrder(
                    "CFM_321"
                )
            )
        }
    }

    @Test
    fun shouldLoadType() {
        runBlocking {
            val cards = subject.loadCards(type = "Minion").awaitFirstData()
            Assert.assertThat(
                cards.map { it.cardId }, containsInAnyOrder(
                    "CFM_321",
                    "EX1_012",
                    "EX1_100",
                    "EX1_556"
                )
            )
        }
    }

    @Test
    fun shouldLoadLegendaryDeathrattleCard() {
        runBlocking {
            val cards = subject.loadCards(mechanic = "Deathrattle", rarity = "Legendary").awaitFirstData()
            Assert.assertThat(
                cards.map { it.cardId }, containsInAnyOrder(
                    "EX1_012"
                )
            )
        }
    }
}