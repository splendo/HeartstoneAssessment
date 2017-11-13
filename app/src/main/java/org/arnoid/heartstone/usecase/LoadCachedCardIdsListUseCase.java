package org.arnoid.heartstone.usecase;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListProvider;
import android.arch.paging.PagedList;

import org.arnoid.heartstone.controller.DatabaseController;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Use case to represent loading cached card id list.
 */
public class LoadCachedCardIdsListUseCase extends LoadCachedUseCase<Flowable<LiveData<PagedList<String>>>> {

    public LoadCachedCardIdsListUseCase(DatabaseController databaseController) {
        super(databaseController);
    }

    public Flowable<LiveData<PagedList<String>>> execute() {
        return getDatabaseController().getCardIdsList(getFilter())
                .map(new Function<LivePagedListProvider<Integer, String>, LiveData<PagedList<String>>>() {
                    @Override
                    public LiveData<PagedList<String>> apply(LivePagedListProvider<Integer, String> livePagedListProvider) throws Exception {
                        return livePagedListProvider.create(getInitialPosition(), producePagedListConfig());
                    }
                });
    }

}
