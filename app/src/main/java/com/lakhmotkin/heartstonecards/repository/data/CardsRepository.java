package com.lakhmotkin.heartstonecards.repository.data;


import com.lakhmotkin.heartstonecards.repository.data.db.DbHelper;
import com.lakhmotkin.heartstonecards.repository.model.CardSets;
import com.lakhmotkin.heartstonecards.repository.model.Card;
import com.lakhmotkin.heartstonecards.repository.service.CardsClientType;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Igor Lakhmotkin on 23.02.2018
 */

@Singleton
public class CardsRepository implements CardsRepositoryType {
    private CardsClientType mCardsClient;
    private DbHelper mDbHelper;

    @Inject
    public CardsRepository(CardsClientType cardsClient, DbHelper dbHelper) {
        mCardsClient = cardsClient;
        mDbHelper = dbHelper;
    }

    @Override
    public Observable<List<Card>> fetchRemoteCards() {
        return Observable.create(e -> {
            CardSets cardSets = mCardsClient.fetchCardSets().blockingFirst();
            e.onNext(cardSets.getAll());
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> insertCards(List<Card> cards) {
        return Observable.create(e -> {
            mDbHelper.saveCardsList(cards).blockingFirst();
            e.onNext(true);
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> updateCard(Card card) {
        return mDbHelper.updateCard(card);
    }

    @Override
    public Observable<List<Card>> getAllCards() {
        return mDbHelper.getAllCards();
    }

    @Override
    public Observable<List<Card>> getAllCardsByMechanic(String mechanic, String rarity) {
        return mDbHelper.getAllCardsByMechanic(mechanic, rarity);
    }

    @Override
    public Observable<List<Card>> getAllCardsByText(String text) {
        return mDbHelper.getAllCardsByText(text);
    }

    @Override
    public Observable<List<Card>> getFavoriteCards() {
        return mDbHelper.getAllFavoriteCards();
    }
}
