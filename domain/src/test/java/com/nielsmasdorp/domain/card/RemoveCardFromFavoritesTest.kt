package com.nielsmasdorp.domain.card

import com.nhaarman.mockito_kotlin.verify
import com.nielsmasdorp.domain.card.data.FavoriteCardsRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Tests for [RemoveCardFromFavorites] use case
 */
@RunWith(MockitoJUnitRunner::class)
class RemoveCardFromFavoritesTest {

    @Mock
    lateinit var favoritesRepository: FavoriteCardsRepository

    @InjectMocks
    lateinit var removeCardFromFavorites: RemoveCardFromFavorites

    @Test
    fun `Remove card from favorites when executed`() {
        // when
        removeCardFromFavorites.execute("1")

        //verify
        verify(favoritesRepository).removeFromFavorites("1")
    }
}