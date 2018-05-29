package com.nielsmasdorp.heartstone.presentation.card.grid.dagger

import com.nielsmasdorp.heartstone.presentation.card.grid.CardGridFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface CardGridSubcomponent : AndroidInjector<CardGridFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CardGridFragment>()
}
