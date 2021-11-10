package com.lakhmotkin.heartstonecards.repository.data.db;

import com.lakhmotkin.heartstonecards.repository.model.Card;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Igor Lakhmotkin on 25.02.2018, for HeartstoneAssessment.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<Card>> getAllCards() {
        return Observable.fromCallable(new Callable<List<Card>>() {
            @Override
            public List<Card> call() throws Exception {
                return mAppDatabase.cardDao().loadAll();
            }
        });
    }

    @Override
    public Observable<List<Card>> getAllCardsByMechanic(String mechanic, String rarity) {
        return Observable.fromCallable(new Callable<List<Card>>() {
            @Override
            public List<Card> call() throws Exception {
                return mAppDatabase.cardDao().loadAllByMechanic(mechanic, rarity);
            }
        });
    }

    @Override
    public Observable<List<Card>> getAllCardsByText(String text) {
        return Observable.fromCallable(new Callable<List<Card>>() {
            @Override
            public List<Card> call() throws Exception {
                return mAppDatabase.cardDao().loadAllByText(text);
            }
        });
    }

    @Override
    public Observable<List<Card>> getAllFavoriteCards() {
        return Observable.fromCallable(new Callable<List<Card>>() {
            @Override
            public List<Card> call() throws Exception {
                return mAppDatabase.cardDao().loadAllFavorites();
            }
        });
    }

    @Override
    public Observable<Boolean> saveCardsList(List<Card> cardsList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.cardDao().insertAll(cardsList);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteAllCards() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.cardDao().deleteAllCards();
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> updateCard(Card card) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.cardDao().insert(card);
                return true;
            }
        });
    }
}
