package org.arnoid.heartstone.usecase;

import org.arnoid.heartstone.controller.DatabaseController;
import org.arnoid.heartstone.controller.NetworkController;
import org.arnoid.heartstone.data.Card;
import org.arnoid.heartstone.data.util.CardSets;
import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;

/**
 * Use case which loads remote data and persists it to db.
 */
public class PullRemoteCardsListAndSaveUseCase {

    private final NetworkController networkController;
    private final DatabaseController databaseController;

    public PullRemoteCardsListAndSaveUseCase(NetworkController networkController, DatabaseController databaseController) {
        this.networkController = networkController;
        this.databaseController = databaseController;
    }

    public Completable execute() {

        Completable completable;
        if (databaseController.isFirstInsert()) {
            completable = databaseController.intialInsert()
                    .andThen(Completable.fromAction(new Action() {
                        @Override
                        public void run() throws Exception {
                            databaseController.setFirstInsert(false);
                        }
                    }));
        } else {
            completable = Completable.complete();
        }

        return completable.andThen(networkController.getCardsList()
                .flatMap(new Function<CardSets, Publisher<List<Card>>>() {
                    @Override
                    public Publisher<List<Card>> apply(CardSets cardSets) throws Exception {
                        return Flowable.fromIterable(cardSets.values());
                    }
                })
                .flatMap(new Function<List<Card>, Publisher<Card>>() {
                    @Override
                    public Publisher<Card> apply(List<Card> cards) throws Exception {
                        return Flowable.fromIterable(cards);
                    }
                })
                .map(databaseController.insert())
                .toList()
                .toCompletable()
        );
    }

}
