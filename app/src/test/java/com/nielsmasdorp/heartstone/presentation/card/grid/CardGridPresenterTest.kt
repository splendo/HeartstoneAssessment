@file:Suppress("IllegalIdentifier")

package com.nielsmasdorp.heartstone.presentation.card.grid

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nielsmasdorp.domain.card.GetCards
import com.nielsmasdorp.domain.card.entity.CardRequest
import com.nielsmasdorp.heartstone.presentation.RxMockitoJUnitRunner
import com.nielsmasdorp.heartstone.presentation.card.CardTestUtil
import com.nielsmasdorp.heartstone.presentation.card.CardViewModel
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock

/**
 * Test for the [CardGridPresenter]
 */
@RunWith(RxMockitoJUnitRunner::class)
class CardGridPresenterTest {

    @Mock
    lateinit var view: CardGrid.View

    @Mock
    lateinit var navigator: CardGrid.Navigator

    @Mock
    lateinit var getCards: GetCards

    @Mock
    lateinit var stringProvider: CardGrid.StringProvider

    @InjectMocks
    lateinit var presenter: CardGridPresenter

    @Test
    fun `Show legendary cards with deathrattle mechanic when presenter starts`() {
        //given
        val request = CardRequest(
                requestedRarity = CardRequest.LEGENDARY_RARITY,
                requestedMechanic = CardRequest.DEATHRATTLE_MECHANIC,
                sortingStrategy = CardRequest.Sort.DESCENDING
        )
        given(getCards.execute(request))
                .willReturn(Single.just(listOf(CardTestUtil.createTestCard(id = "1"))))

        //when
        presenter.startPresenting()

        //then
        verify(view).cards = listOf(CardTestUtil.createTestCardViewModel(id = "1"))
    }

    @Test
    fun `Navigate to card details when card is clicked`() {
        //given
        val viewModel = CardTestUtil.createTestCardViewModel("1")

        //when
        presenter.openCardDetails(viewModel)

        //then
        verify(navigator).openCardDetails(viewModel)
    }
}