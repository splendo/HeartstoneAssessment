package com.lakhmotkin.heartstonecards.repository.data.db;

import com.lakhmotkin.heartstonecards.repository.model.Card;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Igor Lakhmotkin on 25.02.2018, for HeartstoneAssessment.
 */

public interface DbHelper {

    Observable<List<Card>> getAllCards();

    Observable<List<Card>> getAllCardsByMechanic(String mechanic, String rarity);

    Observable<List<Card>> getAllCardsByText(String text);

    Observable<List<Card>> getAllFavoriteCards();

    Observable<Boolean> saveCardsList(List<Card> cardsList);

    Observable<Boolean> deleteAllCards();

    Observable<Boolean> updateCard(Card card);
}
