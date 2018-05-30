@file:Suppress("IllegalIdentifier")

package com.nielsmasdorp.heartstone.presentation.card.carousel

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nielsmasdorp.heartstone.presentation.RxMockitoJUnitRunner
import com.nielsmasdorp.heartstone.presentation.card.CardTestUtil
import com.nielsmasdorp.heartstone.presentation.card.CardViewModel
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock

/**
 * Test for the [CardCarouselPresenter]
 */
@RunWith(RxMockitoJUnitRunner::class)
class CardCarouselPresenterTest {

    @Mock
    lateinit var view: CardCarousel.View

    @Mock
    lateinit var cardsProvided: CardCarousel.CardsProvider

    @InjectMocks
    lateinit var presenter: CardCarouselPresenter

    @Test
    fun `Show cards when presenter starts`() {
        //when
        val viewModels: List<CardViewModel> = listOf(CardTestUtil.createTestCardViewModel(id = "1"))
        given(cardsProvided.cards).willReturn(viewModels)

        //when
        presenter.startPresenting()

        //then
        verify(view).cards = viewModels
    }
}