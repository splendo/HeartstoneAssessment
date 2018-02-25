package com.lakhmotkin.heartstonecards.repository.data;


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

    @Inject
    public CardsRepository(CardsClientType cardsClient) {
        mCardsClient = cardsClient;
    }

    @Override
    public Observable<List<Card>> fetchCards() {
        return Observable.create(e -> {
            CardSets cardSets = mCardsClient.fetchCardSets().blockingFirst();
            e.onNext(cardSets.getAll());
            e.onComplete();
            // TODO: 25.02.2018 return both DB and API results
        });
    }
}
