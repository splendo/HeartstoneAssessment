package nl.splendo.assignment.posytive.data.repositories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import nl.splendo.assignment.posytive.data.DataSource;
import nl.splendo.assignment.posytive.data.remote.RemoteDataSource;
import nl.splendo.assignment.posytive.helpers.NetworkHelper;
import nl.splendo.assignment.posytive.mvp.ModelMVP;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.LinkedList;

/**
 * The entry point to deal with all possible data sources such as {@see RemoteDataSource} or {@see LocalDataSource}
 * Delegates the work to them depending on conditions such as network availability or failures.
 */
@SuppressWarnings("unchecked")
public abstract class BaseDataRepository<M extends BaseModel & ModelMVP> {

    /** Used for logging */
    public static final String TAG = "DataRepository";

    /** The list of fallbacks to find the data, in order as decided by the consumer of this repo */
    protected final LinkedList<DataSource<M>> mFetchingSequence;

    /** The list of already consumed sources, to keep track of which to fall back on.. if needed */
    protected final LinkedList<DataSource<M>> mConsumedSources = new LinkedList<>();

    /** Reference to the (first) DataSource provided that can be also used as cache (if any) */
    protected DataSource.Writable<M> mCachedDataSource;

    /** Flag indicating that this repository is currently fetching data */
    private boolean mBusy = false;

    /**
     * Constructor
     *
     * @param orderedSources 1 or more DataSource(s) of the same type of data, in ordered of how they should be consumed
     */
    public BaseDataRepository(@NonNull DataSource<M>... orderedSources) {
        mFetchingSequence = new LinkedList<>();
        for (DataSource<M> source : orderedSources) {
            if (mCachedDataSource == null && source instanceof DataSource.Writable) {
                mCachedDataSource = (DataSource.Writable) source;
            }
            mFetchingSequence.add(source);
        }
    }

    public boolean isBusy() {
        return mBusy;
    }

    public void setBusy(boolean busy) {
        mBusy = busy;
    }

    /**
     * Selects the next data source in the sequence, if any, and adds it to consumed sources' list.
     * Returns null if it was the last usable source
     *
     * @return the next source to use, or null (if no more sources are available)
     */
    protected  @Nullable
    DataSource<M> useNextSource() {
        DataSource<M> next = null;
        if (hasMoreSources()) {
            next = mFetchingSequence.get(mConsumedSources.size());
            mConsumedSources.add(next);
        }
        // TODO: automatically skip Remote if offline? uncomment next line if so
        // if (!NetworkHelper.isNetworkAvailable() && (next instanceof RemoteDataSource) && hasMoreSources()) next = useNextSource();
        return next;
    }

    /**
     * Returns the DataSource that is currently in use, or that it was used last
     *
     * @return the last used DataSource, possibly still in the process of fetching data
     */
    protected  @Nullable
    DataSource<M> getCurrentSource() {
        return mConsumedSources.getLast();
    }

    protected boolean hasMoreSources() {
        return mFetchingSequence.size() > mConsumedSources.size();
    }

    /**
     * Decides whether or not data should be saved in cache, based on its availability and the type
     * of source that is asking
     *
     * @param source the source holding the data to cache
     * @return true if data can and should be cached, false otherwise
     */
    protected boolean shouldCacheData(DataSource source) {
        return source instanceof DataSource.Expensive
                && mCachedDataSource != null
                && source != mCachedDataSource;
    }
}
