package com.nielsmasdorp.heartstone.presentation.card.detail.dagger

import com.nielsmasdorp.heartstone.presentation.card.detail.CardDetailFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface CardDetailSubcomponent : AndroidInjector<CardDetailFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CardDetailFragment>()
}