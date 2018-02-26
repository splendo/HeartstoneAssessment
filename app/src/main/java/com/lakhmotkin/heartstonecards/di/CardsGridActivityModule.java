package com.lakhmotkin.heartstonecards.di;

import com.lakhmotkin.heartstonecards.view.ui.CardsGridActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by igorlakhmotkin on 23/02/2018.
 */

@Module
public abstract class CardsGridActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract CardsGridActivity cardsGridActivity();
}
