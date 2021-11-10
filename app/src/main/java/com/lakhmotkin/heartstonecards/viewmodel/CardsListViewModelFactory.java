package com.lakhmotkin.heartstonecards.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.lakhmotkin.heartstonecards.repository.data.CardsRepositoryType;


/**
 * Created by Igor Lakhmotkin on 23.02.2018
 */

public class CardsListViewModelFactory implements ViewModelProvider.Factory {
    private final CardsRepositoryType repository;

    public CardsListViewModelFactory(CardsRepositoryType repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CardsListViewModel(repository);
    }
}
