package com.kapanen.hearthstoneassessment.util

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.setting.AppSettings
import com.kapanen.hearthstoneassessment.setting.Storage
import com.kapanen.hearthstoneassessment.util.TestDataModels.cardList
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import com.kapanen.hearthstoneassessment.util.TestDataModels.cards
import com.kapanen.hearthstoneassessment.util.TestDataModels.mockedCard1
import com.kapanen.hearthstoneassessment.util.TestDataModels.mockedCard2
import com.kapanen.hearthstoneassessment.util.TestDataModels.mockedCard3
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito

private const val TYPE_FILTER = "Enchantment|Hero|Minion|Spell|Weapon|Hero Power|Undefined"
private const val RARITY_FILTER = "Free|Common|Legendary|Epic|Rare|Undefined"
private const val CLASS_FILTER =
    "Neutral|Shaman|Priest|Paladin|Warrior|Hunter|Druid|Warlock|Mage|Rogue|Dream|Death Knight|Undefined"
private const val MECHANIC_FILTER =
    "Taunt|OneTurnEffect|Morph|ImmuneToSpellpower|Charge|Battlecry|Freeze|Divine Shield|Aura|Spell Damage|AdjacentBuff|Windfury|Enrage|Summoned|Silence|Stealth|Combo|Overload|Secret|Deathrattle|AffectedBySpellPower|Poisonous|AIMustPlay|zInvisibleDeathrattle|Inspire|Discover|Jade Golem|Adapt|Quest|Undefined"
private const val UNDEFINED = "Undefined"

@RunWith(MockitoJUnitRunner::class)
class CardsExtensionsTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storage: Storage

    @Mock
    private lateinit var resources: Resources

    private lateinit var appSettingsSpy: AppSettings

    @Before
    fun setup() {
        appSettingsSpy = Mockito.spy(AppSettings(storage))
    }

    @Test
    fun testCardsToList() {
        val localCardList: List<Card> = cards.toList()

        assertEquals(localCardList.first { it.cardId == mockedCard1.cardId }, mockedCard1)
        assertEquals(localCardList.first { it.cardId == mockedCard2.cardId }, mockedCard2)
        assertEquals(localCardList.first { it.cardId == mockedCard3.cardId }, mockedCard3)
    }

    @Test
    fun testAscendingSorting() {
        whenever(appSettingsSpy.isAscendingSorting).thenReturn(true)

        val sortedList = cardList.sort(appSettingsSpy)

        assertEquals(sortedList[0], mockedCard2)
        assertEquals(sortedList[1], mockedCard1)
        assertEquals(sortedList[2], mockedCard3)
    }

    @Test
    fun testDescendingSorting() {
        whenever(appSettingsSpy.isAscendingSorting).thenReturn(false)

        val sortedList = cardList.sort(appSettingsSpy)

        assertEquals(sortedList[0], mockedCard3)
        assertEquals(sortedList[1], mockedCard1)
        assertEquals(sortedList[2], mockedCard2)
    }

    @Test
    fun testFilteringFullFilters() {
        whenever(resources.getString(any())).thenReturn(UNDEFINED)
        whenever(appSettingsSpy.typeFilter).thenReturn(TYPE_FILTER)
        whenever(appSettingsSpy.rarityFilter).thenReturn(RARITY_FILTER)
        whenever(appSettingsSpy.classFilter).thenReturn(CLASS_FILTER)
        whenever(appSettingsSpy.mechanicFilter).thenReturn(MECHANIC_FILTER)

        val filteredList = cardList.filter(appSettingsSpy, resources)

        assertEquals(filteredList.size, cardList.size)
        assertEquals(filteredList[0], mockedCard1)
        assertEquals(filteredList[1], mockedCard2)
        assertEquals(filteredList[2], mockedCard3)
    }

    @Test
    fun testFilteringType() {
        whenever(resources.getString(any())).thenReturn(UNDEFINED)
        whenever(appSettingsSpy.typeFilter).thenReturn("Minion")
        whenever(appSettingsSpy.rarityFilter).thenReturn(RARITY_FILTER)
        whenever(appSettingsSpy.classFilter).thenReturn(CLASS_FILTER)
        whenever(appSettingsSpy.mechanicFilter).thenReturn(MECHANIC_FILTER)

        val filteredList = cardList.filter(appSettingsSpy, resources)

        assertEquals(filteredList.size, 2)
        assertEquals(filteredList[0], mockedCard2)
        assertEquals(filteredList[1], mockedCard3)
    }

    @Test
    fun testFilteringRarity() {
        whenever(resources.getString(any())).thenReturn(UNDEFINED)
        whenever(appSettingsSpy.typeFilter).thenReturn(TYPE_FILTER)
        whenever(appSettingsSpy.rarityFilter).thenReturn("Legendary")
        whenever(appSettingsSpy.classFilter).thenReturn(CLASS_FILTER)
        whenever(appSettingsSpy.mechanicFilter).thenReturn(MECHANIC_FILTER)

        val filteredList = cardList.filter(appSettingsSpy, resources)

        assertEquals(filteredList.size, 1)
        assertEquals(filteredList.first(), mockedCard3)
    }

    @Test
    fun testFilteringClass() {
        whenever(resources.getString(any())).thenReturn(UNDEFINED)
        whenever(appSettingsSpy.typeFilter).thenReturn(TYPE_FILTER)
        whenever(appSettingsSpy.rarityFilter).thenReturn(RARITY_FILTER)
        whenever(appSettingsSpy.classFilter).thenReturn("Neutral")
        whenever(appSettingsSpy.mechanicFilter).thenReturn(MECHANIC_FILTER)

        val filteredList = cardList.filter(appSettingsSpy, resources)

        assertEquals(filteredList.size, 2)
        assertEquals(filteredList[0], mockedCard2)
        assertEquals(filteredList[1], mockedCard3)
    }

    @Test
    fun testFilteringMechanic() {
        whenever(resources.getString(any())).thenReturn(UNDEFINED)
        whenever(appSettingsSpy.typeFilter).thenReturn(TYPE_FILTER)
        whenever(appSettingsSpy.rarityFilter).thenReturn(RARITY_FILTER)
        whenever(appSettingsSpy.classFilter).thenReturn(CLASS_FILTER)
        whenever(appSettingsSpy.mechanicFilter).thenReturn("Discover")

        val filteredList = cardList.filter(appSettingsSpy, resources)

        assertEquals(filteredList.size, 1)
        assertEquals(filteredList.first(), mockedCard2)
    }

    @Test
    fun testFilteringUndefined() {
        whenever(resources.getString(any())).thenReturn(UNDEFINED)
        whenever(appSettingsSpy.typeFilter).thenReturn(TYPE_FILTER)
        whenever(appSettingsSpy.rarityFilter).thenReturn(RARITY_FILTER)
        whenever(appSettingsSpy.classFilter).thenReturn(CLASS_FILTER)
        whenever(appSettingsSpy.mechanicFilter).thenReturn(UNDEFINED)

        val filteredList = cardList.filter(appSettingsSpy, resources)

        assertEquals(filteredList.size, 1)
        assertEquals(filteredList.first(), mockedCard3)
    }

}
