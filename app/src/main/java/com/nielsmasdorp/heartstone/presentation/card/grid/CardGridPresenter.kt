package com.nielsmasdorp.heartstone.presentation.card.grid

import com.nielsmasdorp.domain.card.GetCards
import com.nielsmasdorp.domain.card.entity.Card
import com.nielsmasdorp.domain.card.entity.CardRequest
import com.nielsmasdorp.domain.card.entity.CardRequest.Companion.DEATHRATTLE_MECHANIC
import com.nielsmasdorp.domain.card.entity.CardRequest.Companion.LEGENDARY_RARITY
import com.nielsmasdorp.heartstone.presentation.card.CardViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Presenter that presents data for the card grid view
 */
class CardGridPresenter @Inject constructor(private val view: CardGrid.View,
                                            private val navigator: CardGrid.Navigator,
                                            private val stringProvider: CardGrid.StringProvider,
                                            private val getCards: GetCards) : CardGrid.Presenter {

    private var disposable: Disposable? = null

    override fun startPresenting() {
        fetchCards()
    }

    override fun stopPresenting() {
        disposable?.dispose()
    }

    override fun openCardDetails(cardViewModel: CardViewModel) {
        navigator.openCardDetails(cardViewModel)
    }

    private fun fetchCards() {
        val request = CardRequest(
                requestedRarity = LEGENDARY_RARITY,
                requestedMechanic = DEATHRATTLE_MECHANIC,
                sortingStrategy = CardRequest.Sort.DESCENDING
        )
        disposable = getCards.execute(request)
                .map { it.map { mapToViewModel(it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onCards, ::onCardsError)
    }

    private fun mapToViewModel(card: Card): CardViewModel {
        val unknown: String = stringProvider.getUnknownAttributeString()
        return CardViewModel(
                id = card.id,
                name = card.name ?: unknown,
                imgUrl = card.imgUrl ?: "",
                cardSet = card.cardSet ?: unknown,
                type = card.type ?: unknown,
                rarity = card.rarity ?: unknown,
                text = card.text ?: unknown
        )
    }

    private fun onCards(cards: List<CardViewModel>) {
        view.cards = cards
    }

    private fun onCardsError(error: Throwable) {
        Timber.e("error fetching cards", error)
    }
}