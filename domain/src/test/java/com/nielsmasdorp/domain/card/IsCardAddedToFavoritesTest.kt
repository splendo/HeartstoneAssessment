package com.nielsmasdorp.domain.card

import com.nhaarman.mockito_kotlin.given
import com.nielsmasdorp.domain.card.data.CardRepository
import com.nielsmasdorp.domain.card.data.FavoriteCardsRepository
import com.nielsmasdorp.domain.card.entity.CardRequest
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Tests for [isCardAddedToFavorites] use case
 */
@RunWith(MockitoJUnitRunner::class)
class IsCardAddedToFavoritesTest {

    @Mock
    lateinit var favoritesRepository: FavoriteCardsRepository

    @InjectMocks
    lateinit var isCardAddedToFavorites: IsCardAddedToFavorites

    @Test
    fun `Get true when card was added to favorites`() {
        // Given
        given(favoritesRepository.isAddedToFavorites("1")).willReturn(true)

        //verify
        Assert.assertEquals(true, isCardAddedToFavorites.execute("1"))
    }

    @Test
    fun `Get false when card was not added to favorites`() {
        // Given
        given(favoritesRepository.isAddedToFavorites("1")).willReturn(false)

        //verify
        Assert.assertEquals(false, isCardAddedToFavorites.execute("1"))
    }
}