package com.nielsmasdorp.domain.card

import com.nhaarman.mockito_kotlin.given
import com.nielsmasdorp.domain.card.data.CardRepository
import com.nielsmasdorp.domain.card.entity.CardRequest
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Tests for [GetCards] use case
 */
@RunWith(MockitoJUnitRunner::class)
class GetCardsTest {

    @Mock
    lateinit var cardRepository: CardRepository

    @InjectMocks
    lateinit var getCards: GetCards

    @Test
    fun `Get cards return unmodified cards`() {
        // Given
        val card = CardTestUtil.createTestCard(id = "1")
        val request = CardRequest()
        given(cardRepository.getCards(request)).willReturn(Single.just(listOf(card)))

        //when
        val testDisposable = getCards.execute(request).test()

        //verify
        testDisposable.assertValue(listOf(card))
    }
}