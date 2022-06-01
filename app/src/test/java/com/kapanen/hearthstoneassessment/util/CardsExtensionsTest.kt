package com.kapanen.hearthstoneassessment.util

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
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(MockitoJUnitRunner::class)
class CardsExtensionsTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storage: Storage
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

}
