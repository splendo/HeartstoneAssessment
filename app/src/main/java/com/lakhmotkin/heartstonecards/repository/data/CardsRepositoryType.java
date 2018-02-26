package com.lakhmotkin.heartstonecards.repository.data;

import com.lakhmotkin.heartstonecards.repository.model.Card;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Igor Lakhmotkin on 23.02.2018
 */

public interface CardsRepositoryType {
    Observable<List<Card>> fetchRemoteCards();

    Observable<Boolean> insertCards(List<Card> cards);

    Observable<Boolean> updateCard(Card card);

    Observable<List<Card>> getAllCards();

    Observable<List<Card>> getAllCardsByMechanic(String mechanic, String rarity);

    Observable<List<Card>> getAllCardsByText(String text);

    Observable<List<Card>> getFavoriteCards();

}
