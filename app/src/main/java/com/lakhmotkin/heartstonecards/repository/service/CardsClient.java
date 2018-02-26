package com.lakhmotkin.heartstonecards.repository.service;

import com.lakhmotkin.heartstonecards.repository.model.Card;
import com.lakhmotkin.heartstonecards.repository.model.CardSets;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Igor Lakhmotkin on 23.02.2018
 */
@Singleton
public class CardsClient implements CardsClientType {
    private CardsApiClient mCardsApiClient;

    @Inject
    public CardsClient(CardsApiClient cardsApiClient) {
        this.mCardsApiClient = cardsApiClient;
    }

    @Override
    public Observable<CardSets> fetchCardSets() {
        return mCardsApiClient
                .fetchCardSets()
                .subscribeOn(Schedulers.io());
    }
}
