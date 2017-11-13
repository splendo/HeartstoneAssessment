package org.arnoid.heartstone.usecase;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListProvider;
import android.arch.paging.PagedList;

import org.arnoid.heartstone.controller.DatabaseController;
import org.arnoid.heartstone.data.CardComplete;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class LoadCachedCardsListUseCase implements UseCase<Flowable<LiveData<PagedList<CardComplete>>>> {

    public static final int DEFAULT_PAGE_SIZE = 100;
    public static final int DEFAULT_PREFETCH_SIZE = 100;

    private final DatabaseController databaseController;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int prefetchDistance = DEFAULT_PREFETCH_SIZE;

    public LoadCachedCardsListUseCase(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public LoadCachedCardsListUseCase setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public LoadCachedCardsListUseCase setPrefetchDistance(int prefetchDistance) {
        this.prefetchDistance = prefetchDistance;

        return this;
    }

    @Override
    public Flowable<LiveData<PagedList<CardComplete>>> execute() {
        return databaseController.getCardsList()
                .map(new Function<LivePagedListProvider<Integer, CardComplete>, LiveData<PagedList<CardComplete>>>() {
                    @Override
                    public LiveData<PagedList<CardComplete>> apply(LivePagedListProvider<Integer, CardComplete> livePagedListProvider) throws Exception {
                        PagedList.Config config = new PagedList.Config.Builder()
                                .setPageSize(pageSize)
                                .setPrefetchDistance(prefetchDistance)
                                .build();

                        return livePagedListProvider.create(null, config);
                    }
                });
    }

}
