package org.arnoid.heartstone.usecase;

import android.arch.paging.PagedList;

import org.arnoid.heartstone.controller.DatabaseController;
import org.arnoid.heartstone.data.util.CardsFilter;
/**
 * Base class loading cached card data use cases.
 */
public abstract class LoadCachedUseCase<T>{

    public static final int DEFAULT_PAGE_SIZE = 100;
    public static final int DEFAULT_PREFETCH_SIZE = 100;
    public static final int DEFAULT_INITIAL_POSITION = 0;

    private final DatabaseController databaseController;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int prefetchDistance = DEFAULT_PREFETCH_SIZE;
    private int initialPosition = DEFAULT_PREFETCH_SIZE;

    private CardsFilter filter;

    protected LoadCachedUseCase(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPrefetchDistance(int prefetchDistance) {
        this.prefetchDistance = prefetchDistance;
    }

    public void setFilter(CardsFilter cardsFilter) {
        this.filter = cardsFilter;
    }

    public void setInitialPosition(int initialPosition) {
        this.initialPosition = initialPosition;
    }

    DatabaseController getDatabaseController() {
        return databaseController;
    }

    int getPageSize() {
        return pageSize;
    }

    int getPrefetchDistance() {
        return prefetchDistance;
    }

    int getInitialPosition() {
        return initialPosition;
    }

    public CardsFilter getFilter() {
        return filter;
    }

    PagedList.Config producePagedListConfig() {
        return new PagedList.Config.Builder()
                .setPageSize(getPageSize())
                .setPrefetchDistance(getPrefetchDistance())
                .build();
    }
}
