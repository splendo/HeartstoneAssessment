package nl.splendo.assignment.posytive.helpers;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import static nl.splendo.assignment.posytive.presenters.generics.ItemListPresenter.PAGINATION_SIZE;

/**
 * A listener to be added to {@link RecyclerView#addOnScrollListener(RecyclerView.OnScrollListener)}
 * to handle pagination on scrolling.
 */
public abstract class PaginatedRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    /** The minimum amount of items to have below your current scroll position  before loading more. */
    private int mVisibleThreshold = PAGINATION_SIZE;

    /** Sets the starting page index */
    private int mStartingPageIndex = 1;

    /** The current offset index of data you have loaded */
    private int mCurrentPage = mStartingPageIndex;

    /** The total number of items in the dataset after the last load */
    private int mPreviousTotalItemCount = 0;

    /** True if we are still waiting for the last set of data to load. */
    private boolean mIsLoading = true;

    /**
     * True if we more data should be loaded when scrolling, false when it shouldn't.
     * (Typically after all data has been loaded)
     */
    private boolean mIsEnabled = true;

    private RecyclerView.LayoutManager mLayoutManager;

    public PaginatedRecyclerViewScrollListener(LinearLayoutManager layoutManager, int startingPageIndex) {
        mLayoutManager = layoutManager;
        mStartingPageIndex = startingPageIndex;
    }

    public PaginatedRecyclerViewScrollListener(GridLayoutManager layoutManager,
                                               int startingPageIndex) {
        mLayoutManager = layoutManager;
        mVisibleThreshold *= layoutManager.getSpanCount();
        mStartingPageIndex = startingPageIndex;
    }

    public PaginatedRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager,
                                               int startingPageIndex) {
        mLayoutManager = layoutManager;
        mVisibleThreshold *= layoutManager.getSpanCount();
        mStartingPageIndex = startingPageIndex;
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        mIsEnabled = isEnabled;
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    /**
     * This happens many times a second during a scroll
     * We are given a few useful parameters to help us work out if we need to load some more data,
     * but first we check if we are waiting for the previous load to finish.
     */
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        if (isEnabled()) {
            int lastVisibleItemPosition = 0;
            int totalItemCount = mLayoutManager.getItemCount();

            if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                int[] lastVisibleItemPositions =
                        ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(
                                null);
                // get maximum element within the list
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
            } else if (mLayoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition =
                        ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            } else if (mLayoutManager instanceof LinearLayoutManager) {
                lastVisibleItemPosition =
                        ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            }

            // If the total item count is zero and the previous isn't, assume the
            // list is invalidated and should be reset back to initial state
            if (totalItemCount < mPreviousTotalItemCount) {
                mCurrentPage = mStartingPageIndex;
                mPreviousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) {
                    mIsLoading = true;
                }
            }
            // If it’s still loading, we check to see if the dataset count has
            // changed, if so we conclude it has finished loading and update the current page
            // number and total item count.
            if (mIsLoading && (totalItemCount > mPreviousTotalItemCount)) {
                mIsLoading = false;
                mPreviousTotalItemCount = totalItemCount;
            }

            // If it isn’t currently loading, we check to see if we have breached
            // the visibleThreshold and need to reload more data.
            // If we do need to reload some more data, we execute onLoadMore to fetch the data.
            // threshold should reflect how many total columns there are too
            if (!mIsLoading && (lastVisibleItemPosition + mVisibleThreshold) > totalItemCount) {
                onLoadMore(++mCurrentPage, totalItemCount, view);
                mIsLoading = true;
            }
        }
    }

    // Call this method whenever performing new searches
    public void resetState() {
        mCurrentPage = mStartingPageIndex;
        mPreviousTotalItemCount = 0;
        mIsLoading = true;
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
}
