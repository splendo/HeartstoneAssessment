package com.nielsmasdorp.heartstone.presentation.card.detail.dagger

import android.support.v4.app.Fragment
import com.nielsmasdorp.domain.card.data.FavoriteCardsRepository
import com.nielsmasdorp.heartstone.data.card.SharedPrefsFavoritesCardRepository
import com.nielsmasdorp.heartstone.presentation.card.detail.CardDetail
import com.nielsmasdorp.heartstone.presentation.card.detail.CardDetailFragment
import com.nielsmasdorp.heartstone.presentation.card.detail.CardDetailPresenter
import com.nielsmasdorp.heartstone.presentation.card.detail.CardProvider
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [CardDetailSubcomponent::class])
abstract class CardDetailFragmentModule {

    @Binds
    @IntoMap
    @FragmentKey(CardDetailFragment::class)
    internal abstract fun bindCardDetailFragmentInjectorFactory(builder: CardDetailSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    abstract fun bindCardDetailView(cardDetailFragment: CardDetailFragment): CardDetail.View

    @Binds
    abstract fun bindCardDetailPresenter(cardDetailPresenter: CardDetailPresenter): CardDetail.Presenter

    @Binds
    abstract fun bindCardProvider(cardProvider: CardProvider): CardDetail.CardProvider

    @Binds
    abstract fun bindFavoritesRepository(sharedPrefsFavoritesCardRepository: SharedPrefsFavoritesCardRepository): FavoriteCardsRepository
}