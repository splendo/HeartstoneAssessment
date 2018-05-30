@file:Suppress("IllegalIdentifier")

package com.nielsmasdorp.heartstone.presentation.card.details

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nielsmasdorp.domain.card.AddCardToFavorites
import com.nielsmasdorp.domain.card.IsCardAddedToFavorites
import com.nielsmasdorp.domain.card.RemoveCardFromFavorites
import com.nielsmasdorp.heartstone.presentation.RxMockitoJUnitRunner
import com.nielsmasdorp.heartstone.presentation.card.CardTestUtil
import com.nielsmasdorp.heartstone.presentation.card.detail.CardDetail
import com.nielsmasdorp.heartstone.presentation.card.detail.CardDetailPresenter
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock

/**
 * Test for the [CardDetailsPresenter]
 */
@RunWith(RxMockitoJUnitRunner::class)
class CardDetailsPresenterTest {

    @Mock
    lateinit var view: CardDetail.View

    @Mock
    lateinit var cardProvider: CardDetail.CardProvider

    @Mock
    lateinit var isCardAddedToFavorites: IsCardAddedToFavorites

    @Mock
    lateinit var addCardToFavorites: AddCardToFavorites

    @Mock
    lateinit var removeCardFromFavorites: RemoveCardFromFavorites

    @InjectMocks
    lateinit var presenter: CardDetailPresenter

    @Test
    fun `Show provided card when presenter starts`() {
        //when
        val viewModel = CardTestUtil.createTestCardViewModel(id = "1")
        given(cardProvider.card).willReturn(viewModel)

        //when
        presenter.startPresenting()

        //then
        verify(view).card = viewModel
    }

    @Test
    fun `Show favorite card when presenter starts`() {
        //when
        val viewModel = CardTestUtil.createTestCardViewModel(id = "1")
        given(cardProvider.card).willReturn(viewModel)
        given(isCardAddedToFavorites.execute("1")).willReturn(true)

        //when
        presenter.startPresenting()

        //then
        verify(view).showCardAsFavorite(notifyUser = false)
    }

    @Test
    fun `Add correct card to favorites when card is clicked and it was not added to the favorites`() {
        //given
        given(view.card).willReturn(CardTestUtil.createTestCardViewModel(id = "1"))
        given(isCardAddedToFavorites.execute("1")).willReturn(false)

        //when
        presenter.onAddToFavoritesClicked()

        //then
        verify(addCardToFavorites).execute("1")
    }

    @Test
    fun `Remove correct card from favorites when card is clicked and it was already added to the favorites`() {
        //given
        given(view.card).willReturn(CardTestUtil.createTestCardViewModel(id = "1"))
        given(isCardAddedToFavorites.execute("1")).willReturn(true)

        //when
        presenter.onAddToFavoritesClicked()

        //then
        verify(removeCardFromFavorites).execute("1")
    }
}