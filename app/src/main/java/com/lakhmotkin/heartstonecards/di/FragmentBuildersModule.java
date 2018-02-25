package com.lakhmotkin.heartstonecards.di;

import com.lakhmotkin.heartstonecards.view.ui.CardFragment;
import com.lakhmotkin.heartstonecards.view.ui.CardsPagerFragment;
import com.lakhmotkin.heartstonecards.view.ui.CardsGridFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by igorlakhmotkin on 23/02/2018.
 */

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract CardsGridFragment contributeCardsGridFragment();

    @ContributesAndroidInjector
    abstract CardsPagerFragment contributeCardsPagerFragment();

    @ContributesAndroidInjector
    abstract CardFragment contributeCardPageFragment();
}
