package org.arnoid.heartstone.usecase;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListProvider;
import android.arch.paging.PagedList;

import org.arnoid.heartstone.controller.DatabaseController;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class LoadCachedCardIdsListUseCase implements UseCase<Flowable<LiveData<PagedList<String>>>> {

    public static final int DEFAULT_PAGE_SIZE = 100;
    public static final int DEFAULT_PREFETCH_SIZE = 100;

    private final DatabaseController databaseController;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int prefetchDistance = DEFAULT_PREFETCH_SIZE;

    public LoadCachedCardIdsListUseCase(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public LoadCachedCardIdsListUseCase setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public LoadCachedCardIdsListUseCase setPrefetchDistance(int prefetchDistance) {
        this.prefetchDistance = prefetchDistance;

        return this;
    }

    @Override
    public Flowable<LiveData<PagedList<String>>> execute() {
        return databaseController.getCardIdsList()
                .map(new Function<LivePagedListProvider<Integer, String>, LiveData<PagedList<String>>>() {
                    @Override
                    public LiveData<PagedList<String>> apply(LivePagedListProvider<Integer, String> livePagedListProvider) throws Exception {
                        PagedList.Config config = new PagedList.Config.Builder()
                                .setPageSize(pageSize)
                                .setPrefetchDistance(prefetchDistance)
                                .build();

                        return livePagedListProvider.create(null, config);
                    }
                });
    }

}
