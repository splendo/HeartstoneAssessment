package org.arnoid.heartstone.usecase;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import org.arnoid.heartstone.controller.DatabaseController;
import org.arnoid.heartstone.data.CardComplete;

import io.reactivex.Flowable;

public class CachedCardsListUseCase implements UseCase<Flowable<LiveData<PagedList<CardComplete>>>> {

    private final DatabaseController databaseController;

    public CachedCardsListUseCase(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    @Override
    public Flowable<LiveData<PagedList<CardComplete>>> execute() {
        return databaseController.getCardsList();
    }

}
