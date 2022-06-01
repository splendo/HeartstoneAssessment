package com.kapanen.hearthstoneassessment.util

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DbExtensionsTest {

    @Test
    fun testDbCardToCard() {
        val dbCards = TestDataModels.dbCardList

        val cards = dbCards.map { dbCard -> dbCard.toCard() }

        assertEquals(cards[0], TestDataModels.cardList[0])
        assertEquals(cards[1], TestDataModels.cardList[1])
        assertEquals(cards[2], TestDataModels.cardList[2])
    }

    @Test
    fun testCardToDbCard() {
        val cards = TestDataModels.cardList

        val dBCards = cards.map { card -> card.toDbCard() }

        assertEquals(dBCards[0], TestDataModels.dbCardList[0])
        assertEquals(dBCards[1], TestDataModels.dbCardList[1])
        assertEquals(dBCards[2], TestDataModels.dbCardList[2])
    }

}
