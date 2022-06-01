package com.kapanen.hearthstoneassessment.util

import com.kapanen.hearthstoneassessment.model.Card
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardsExtensionsTest {

    @Test
    fun testCardsToList() {
        val localCardList: List<Card> = TestDataModels.cards.toList()
        assertEquals(
            localCardList.first { it.cardId == TestDataModels.mockedCard1.cardId },
            TestDataModels.mockedCard1
        )
        assertEquals(
            localCardList.first { it.cardId == TestDataModels.mockedCard2.cardId },
            TestDataModels.mockedCard2
        )
        assertEquals(
            localCardList.first { it.cardId == TestDataModels.mockedCard3.cardId },
            TestDataModels.mockedCard3
        )
    }

}
