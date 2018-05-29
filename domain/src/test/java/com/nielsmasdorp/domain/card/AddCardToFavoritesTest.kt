package com.nielsmasdorp.domain.card

import com.nhaarman.mockito_kotlin.verify
import com.nielsmasdorp.domain.card.data.FavoriteCardsRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Tests for [AddCardToFavorites] use case
 */
@RunWith(MockitoJUnitRunner::class)
class AddCardToFavoritesTest {

    @Mock
    lateinit var favoritesRepository: FavoriteCardsRepository

    @InjectMocks
    lateinit var addCardToFavorites: AddCardToFavorites

    @Test
    fun `Save card to favorites when executed`() {
        // when
        addCardToFavorites.execute("1")

        //verify
        verify(favoritesRepository).addToFavorites("1")
    }
}