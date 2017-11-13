package org.arnoid.heartstone.usecase;

import org.arnoid.heartstone.controller.DatabaseController;
import org.arnoid.heartstone.data.Card;

import io.reactivex.Completable;

public class UpdateCardFavouriteStatusUseCase{

    private final DatabaseController databaseController;
    private Card cardToUpdate;

    public UpdateCardFavouriteStatusUseCase(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void setCardToUpdate(Card cardToUpdate) {
        this.cardToUpdate = cardToUpdate;
    }

    public Completable execute() {
        return this.databaseController.update(cardToUpdate);
    }
}
