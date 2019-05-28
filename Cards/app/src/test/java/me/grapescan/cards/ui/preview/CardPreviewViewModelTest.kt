package me.grapescan.cards.ui.preview

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
class CardPreviewViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val stubInitialCard = card("1")
    private val stubCards = listOf(card("1"), card("2"), card("3"))
    private val query = CardRepository.Query(
        CardRepository.Filter(
            mechanics = CardRepository.Mechanics.DEATHRATTLE,
            rarity = CardRepository.Rarity.LEGENDARY
        )
    )

    @Mock
    private lateinit var cardRepositoryMock: CardRepository

    private lateinit var target: CardPreviewViewModel

    @Before
    fun setup() {
        target = CardPreviewViewModel(stubInitialCard, cardRepositoryMock)
    }

    @Test
    fun testSuccessfulDataLoading() = runBlocking {
        given(cardRepositoryMock.getCurrentSelection()).willReturn(stubCards)
        target.cards.test()
            .awaitValue()
            .assertHasValue()
            .assertValue { it == stubCards }
        target.currentCard.test()
            .awaitValue()
            .assertHasValue()
            .assertValue { it == stubInitialCard }
        verify(cardRepositoryMock).getCurrentSelection()
        verifyNoMoreInteractions(cardRepositoryMock)
    }.toUnit()
}



