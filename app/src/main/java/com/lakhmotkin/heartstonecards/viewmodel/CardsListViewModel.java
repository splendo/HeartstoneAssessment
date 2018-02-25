package com.lakhmotkin.heartstonecards.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.lakhmotkin.heartstonecards.repository.data.CardsRepositoryType;
import com.lakhmotkin.heartstonecards.repository.model.Card;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Igor Lakhmotkin on 24.02.2018
 */

public class CardsListViewModel extends ViewModel {
    private final MutableLiveData<Boolean> mProgress = new MutableLiveData<>();
    private final MutableLiveData<List<Card>> mCards = new MutableLiveData<>();
    private final MutableLiveData<Throwable> mError = new MutableLiveData<>();
    private final CardsRepositoryType mProductRepository;

    @Inject
    public CardsListViewModel(@NonNull CardsRepositoryType productRepository) {
        mProductRepository = productRepository;
    }

    public void prepare() {
        if (mCards.getValue() == null) {
            fetch();
        }
    }

    public void fetch() {
        mProgress.postValue(true);
        mProductRepository
                .fetchCards()
                .subscribe(this::onCardsList, this::onError);
    }

    private void onError(Throwable throwable) {
        mProgress.postValue(false);
        this.mError.postValue(throwable);
    }

    private void onCardsList(List<Card> categories) {
        mProgress.postValue(false);
        this.mCards.postValue(categories);
    }

    public LiveData<Boolean> progress() {
        return mProgress;
    }

    public LiveData<List<Card>> cards() {
        return mCards;
    }

    public LiveData<Throwable> error() {
        return mError;
    }
}
