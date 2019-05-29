package me.grapescan.cards.data

import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import me.grapescan.cards.data.storage.Storage
import me.grapescan.cards.ext.toUnit
import me.grapescan.cards.utils.card
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class LocalCardRepositoryTest {

    companion object {

        private val card1 = card(
            "1", "1", "1", CardRepository.Type.ENCHANTMENT.id,
            CardRepository.Rarity.LEGENDARY.id, 1, 1, 1, "1", "1", "1",
            false, CardRepository.PlayerClass.DEATH_KNIGHT.id, "1", "1",
            "1", listOf(CardRepository.Mechanics.ADAPT.id, CardRepository.Mechanics.AURA.id)
        )
        private val card2 = card(
            "W", "W", "W", CardRepository.Type.HERO.id,
            CardRepository.Rarity.COMMON.id, 2, 2, 2, "W", "W", "W",
            true, CardRepository.PlayerClass.HUNTER.id, "W", "W",
            "W", listOf(CardRepository.Mechanics.AURA.id, CardRepository.Mechanics.DEATHRATTLE.id)
        )
        private val card3 = card(
            "Л", "Л", "Л", CardRepository.Type.SPELL.id,
            CardRepository.Rarity.EPIC.id, 1000, 1000, 1000, "Л", "Л", "Л",
            true, CardRepository.PlayerClass.PALADIN.id, "Л", "Л",
            "Л", emptyList()
        )
        private val mixedCardsStub = listOf(card2, card3, card1)
        private val sortedByNameCardsStub = listOf(card1, card2, card3)
    }

    @Mock
    private lateinit var favoritesStorageMock: Storage<List<String>>

    @Mock
    private lateinit var catalogStorageMock: Storage<List<Card>>

    private lateinit var target: LocalCardRepository

    @Before
    fun setup() {
        target = LocalCardRepository(favoritesStorageMock, catalogStorageMock)
    }

    @Test
    fun testInitialState() = runBlocking {
        given(favoritesStorageMock.load()).willReturn(emptyList())
        given(catalogStorageMock.load()).willReturn(mixedCardsStub)
        target.getCurrentSelection() shouldEqual sortedByNameCardsStub
        target.getCards() shouldEqual sortedByNameCardsStub
        target.getCurrentSelection() shouldEqual sortedByNameCardsStub
    }.toUnit()

    @Test
    fun testFavoritesBinding() = runBlocking {
        given(favoritesStorageMock.load()).willReturn(listOf(card1.id))
        given(catalogStorageMock.load()).willReturn(mixedCardsStub)
        target.getCards() shouldEqual listOf(card1.copy(isFavorite = true), card2, card3)
    }.toUnit()

    @Test
    fun testSortingByName() = testSorting(CardRepository.Parameter.NAME, listOf(card1, card2, card3))

    @Test
    fun testSortingByArtist() = testSorting(CardRepository.Parameter.ARTIST, listOf(card1, card2, card3))

    @Test
    fun testSortingByAttack() = testSorting(CardRepository.Parameter.ATTACK, listOf(card1, card2, card3))

    @Test
    fun testSortingByHealth() = testSorting(CardRepository.Parameter.HEALTH, listOf(card1, card2, card3))

    @Test
    fun testSortingByCost() = testSorting(CardRepository.Parameter.COST, listOf(card1, card2, card3))

    @Test
    fun testSortingByCardSet() = testSorting(CardRepository.Parameter.CARD_SET, listOf(card1, card2, card3))

    @Test
    fun testSortingByCollectible() =
        testSorting(CardRepository.Parameter.COLLECTIBLE, listOf(card1, card2), listOf(card2, card1))

    @Test
    fun testSortingByFlavor() = testSorting(CardRepository.Parameter.FLAVOR, listOf(card1, card2, card3))

    @Test
    fun testSortingByPlayerClass() = testSorting(CardRepository.Parameter.PLAYER_CLASS, listOf(card1, card2, card3))

    @Test
    fun testSortingByRarity() = testSorting(CardRepository.Parameter.RARITY, listOf(card2, card3, card1))

    @Test
    fun testSortingByText() = testSorting(CardRepository.Parameter.TEXT, listOf(card1, card2, card3))

    @Test
    fun testSortingByType() = testSorting(CardRepository.Parameter.TYPE, listOf(card1, card2, card3))

    @Test
    fun testFilterByType() {
        testFiltering(mixedCardsStub, sortedByNameCardsStub, type = CardRepository.Type.ANY)
        testFiltering(mixedCardsStub, listOf(card1), type = CardRepository.Type.ENCHANTMENT)
        testFiltering(mixedCardsStub, emptyList(), type = CardRepository.Type.WEAPON)
    }

    @Test
    fun testFilterByRarity() {
        testFiltering(mixedCardsStub, sortedByNameCardsStub, rarity = CardRepository.Rarity.ANY)
        testFiltering(mixedCardsStub, listOf(card3), rarity = CardRepository.Rarity.EPIC)
        testFiltering(mixedCardsStub, emptyList(), rarity = CardRepository.Rarity.RARE)
    }

    @Test
    fun testFilterByPlayerClass() {
        testFiltering(mixedCardsStub, sortedByNameCardsStub, playerClass = CardRepository.PlayerClass.ANY)
        testFiltering(mixedCardsStub, listOf(card3), playerClass = CardRepository.PlayerClass.PALADIN)
        testFiltering(mixedCardsStub, emptyList(), playerClass = CardRepository.PlayerClass.ROGUE)
    }

    @Test
    fun testFilterByMechanics() {
        testFiltering(mixedCardsStub, sortedByNameCardsStub, mechanics = CardRepository.Mechanics.ANY)
        testFiltering(mixedCardsStub, listOf(card1), mechanics = CardRepository.Mechanics.ADAPT)
        testFiltering(mixedCardsStub, listOf(card1, card2), mechanics = CardRepository.Mechanics.AURA)
        testFiltering(mixedCardsStub, emptyList(), mechanics = CardRepository.Mechanics.CHARGE)
    }

    @Test
    fun testFilterBySeveralParams() {
        testFiltering(
            mixedCardsStub, sortedByNameCardsStub,
            type = CardRepository.Type.ANY,
            rarity = CardRepository.Rarity.ANY,
            playerClass = CardRepository.PlayerClass.ANY,
            mechanics = CardRepository.Mechanics.ANY
        )
        testFiltering(
            mixedCardsStub, listOf(card1),
            type = CardRepository.Type.ENCHANTMENT,
            mechanics = CardRepository.Mechanics.AURA
        )
        testFiltering(
            mixedCardsStub, emptyList(),
            playerClass = CardRepository.PlayerClass.HUNTER,
            rarity = CardRepository.Rarity.EPIC
        )
    }

    private fun testSorting(
        parameter: CardRepository.Parameter,
        expectedAscendingResult: List<Card>,
        source: List<Card> = mixedCardsStub
    ) {
        testSorting(parameter, true, source, expectedAscendingResult)
        testSorting(parameter, false, source, expectedAscendingResult.reversed())
    }

    private fun testFiltering(
        source: List<Card>,
        expectedResult: List<Card>,
        type: CardRepository.Type = CardRepository.Type.ANY,
        rarity: CardRepository.Rarity = CardRepository.Rarity.ANY,
        playerClass: CardRepository.PlayerClass = CardRepository.PlayerClass.ANY,
        mechanics: CardRepository.Mechanics = CardRepository.Mechanics.ANY
    ) = testQuery(
        CardRepository.Query(filter = CardRepository.Filter(type, rarity, playerClass, mechanics)),
        source,
        expectedResult
    )

    private fun testSorting(
        parameter: CardRepository.Parameter,
        isAscending: Boolean,
        source: List<Card>,
        expectedResult: List<Card>
    ) =
        testQuery(
            CardRepository.Query(sorting = CardRepository.Sorting(parameter, isAscending)),
            source,
            expectedResult
        )

    private fun testQuery(query: CardRepository.Query, source: List<Card>, expectedesult: List<Card>) = runBlocking {
        given(favoritesStorageMock.load()).willReturn(emptyList())
        given(catalogStorageMock.load()).willReturn(source)
        target.getCards(query) shouldEqual expectedesult
    }.toUnit()

}
