package me.grapescan.cards.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import me.grapescan.cards.data.CardRepository
import me.grapescan.cards.ext.toUnit
import me.grapescan.cards.utils.card
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class CardListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val stubCards = listOf(card("1"), card("2"), card("3"))
    private val query = CardRepository.Query(
        CardRepository.Filter(
            mechanics = CardRepository.Mechanics.DEATHRATTLE,
            rarity = CardRepository.Rarity.LEGENDARY
        )
    )

    @Mock
    private lateinit var cardRepositoryMock: CardRepository

    private lateinit var target: CardListViewModel

    @Before
    fun setup() {
        target = CardListViewModel(cardRepositoryMock)
    }

    @Test
    fun testSuccessfulDataLoading() = runBlocking {
        given(cardRepositoryMock.getCards(query)).willReturn(stubCards)
        target.cards.test()
            .awaitValue()
            .assertHasValue()
            .assertValue { it == stubCards }
        verify(cardRepositoryMock).getCards(query)
        verifyNoMoreInteractions(cardRepositoryMock)
    }.toUnit()
}



