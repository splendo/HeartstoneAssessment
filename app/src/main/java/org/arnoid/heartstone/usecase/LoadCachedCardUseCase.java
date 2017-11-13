package org.arnoid.heartstone.usecase;

import org.arnoid.heartstone.controller.DatabaseController;
import org.arnoid.heartstone.data.Card;

import io.reactivex.Flowable;

/**
 * Use case to represent loading cached card.
 */
public class LoadCachedCardUseCase extends LoadCachedUseCase<Flowable<Card>> {

    private String cardId;

    public LoadCachedCardUseCase(DatabaseController databaseController) {
        super(databaseController);
    }

    public LoadCachedCardUseCase setCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }


    public Flowable<Card> execute() {
        return getDatabaseController().getCard(cardId);
    }

}
