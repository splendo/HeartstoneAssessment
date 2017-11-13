package org.arnoid.heartstone.usecase;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListProvider;
import android.arch.paging.PagedList;

import org.arnoid.heartstone.controller.DatabaseController;
import org.arnoid.heartstone.data.Card;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Use case to represent loading cached card list.
 */
public class LoadCachedCardsListUseCase extends LoadCachedUseCase<Flowable<LiveData<PagedList<Card>>>> {

    public LoadCachedCardsListUseCase(DatabaseController databaseController) {
        super(databaseController);
    }

    public Flowable<LiveData<PagedList<Card>>> execute() {
        return getDatabaseController().getCardsList(getFilter())
                .map(new Function<LivePagedListProvider<Integer, Card>, LiveData<PagedList<Card>>>() {
                    @Override
                    public LiveData<PagedList<Card>> apply(LivePagedListProvider<Integer, Card> livePagedListProvider) throws Exception {
                        return livePagedListProvider.create(getInitialPosition(), producePagedListConfig());
                    }
                });
    }

}
