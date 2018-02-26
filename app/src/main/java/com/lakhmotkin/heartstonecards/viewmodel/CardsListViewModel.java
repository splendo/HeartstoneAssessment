package com.lakhmotkin.heartstonecards.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.lakhmotkin.heartstonecards.repository.data.CardsRepositoryType;
import com.lakhmotkin.heartstonecards.repository.model.Card;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Igor Lakhmotkin on 24.02.2018
 */

public class CardsListViewModel extends ViewModel {
    private final MutableLiveData<Boolean> mProgress = new MutableLiveData<>();
    private final MutableLiveData<List<Card>> mCards = new MutableLiveData<>();
    private final MutableLiveData<Throwable> mError = new MutableLiveData<>();
    private final CardsRepositoryType mCardsRepository;

    @Inject
    public CardsListViewModel(@NonNull CardsRepositoryType cardsRepository) {
        mCardsRepository = cardsRepository;
    }

    public void prepare() {
        if (mCards.getValue() == null) {
            fetchFavorites();
        }
    }

    public void fetchRemote() {
        mProgress.postValue(true);
        mCardsRepository
                .fetchRemoteCards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRemoteCardsList, this::onError);
    }

    public void fetchLocal() {
        mProgress.postValue(true);
        mCardsRepository
                .getAllCards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLocalCardsList, this::onErrorLocal);
    }

    public void fetchFavorites() {
        mProgress.postValue(true);
        mCardsRepository
                .getFavoriteCards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFavoriteCardList, this::onError);
    }

    private void onErrorLocal(Throwable throwable) {
        fetchRemote();
    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
        mProgress.postValue(false);
        this.mError.postValue(throwable);
    }

    private void onLocalCardsList(List<Card> cards) {
        mProgress.postValue(false);
        if (!cards.isEmpty()) {
            this.mCards.postValue(cards);
        } else {
            fetchRemote();
        }
    }

    private void onFavoriteCardList(List<Card> cards) {
        mProgress.postValue(false);
        this.mCards.postValue(cards);
    }

    private void onRemoteCardsList(List<Card> cards) {
        mProgress.postValue(false);
        this.mCards.postValue(cards);
        mCardsRepository
                .insertCards(cards)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onCardsInserted, this::onError);
    }

    private void onCardsInserted(Boolean result) {
    }


    private void onCardUpdated(Boolean result) {
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

    public Observable<Boolean> addToFavorites(Card card) {
        return mCardsRepository.updateCard(card);
    }
}
