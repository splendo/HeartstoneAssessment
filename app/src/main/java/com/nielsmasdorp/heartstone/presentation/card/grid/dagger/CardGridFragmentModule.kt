package com.nielsmasdorp.heartstone.presentation.card.grid.dagger

import android.support.v4.app.Fragment
import com.nielsmasdorp.domain.card.data.CardRepository
import com.nielsmasdorp.heartstone.data.card.JSONCardRepository
import com.nielsmasdorp.heartstone.presentation.card.grid.CardGrid
import com.nielsmasdorp.heartstone.presentation.card.grid.CardGridFragment
import com.nielsmasdorp.heartstone.presentation.card.grid.CardGridPresenter
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [CardGridSubcomponent::class])
abstract class CardGridFragmentModule {

    @Binds
    @IntoMap
    @FragmentKey(CardGridFragment::class)
    internal abstract fun bindCardGridFragmentInjectorFactory(builder: CardGridSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    abstract fun bindCardGridView(cardGridFragment: CardGridFragment): CardGrid.View

    @Binds
    abstract fun bindCardGridPresenter(cardGridPresenter: CardGridPresenter): CardGrid.Presenter

    @Binds
    abstract fun bindCardGridNavigator(cardGridFragment: CardGridFragment): CardGrid.Navigator

    @Binds
    abstract fun bindCardGridStringProvider(cardGridFragment: CardGridFragment): CardGrid.StringProvider

    @Binds
    abstract fun bindCardRepository(memoryCardRepository: JSONCardRepository): CardRepository
}