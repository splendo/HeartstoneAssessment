package nl.splendo.assignment.posytive.data.repositories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import nl.splendo.assignment.posytive.data.DataSource;
import nl.splendo.assignment.posytive.helpers.NetworkHelper;
import nl.splendo.assignment.posytive.mvp.ModelMVP;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * The entry point to deal with all possible data sources such as {@see RemoteDataSource},
 * {@see LocalDataSource} or {@see CachedDataSource}
 * Delegates the work to them depending on conditions such as network availability or failures.
 */
@SuppressWarnings("unchecked")
public class DataCollectionRepository<M extends BaseModel & ModelMVP> extends BaseDataRepository<M> {

    /** Used for logging */
    public static final String TAG = "DataListRepository";

    /** Reference to the external callback to notify about a collection of items, valid while the data is fetched */
    protected DataSource.GetDataCallback<M> mCollectionCallback;

    /** If is a paginated call, keep track of the page to keep requesting it consistently. Negative means null */
    private int mPaginationIndex = -1;

    /**
     * Constructor
     *
     * @param orderedSources 1 or more DataSource(s) of the same type of data, in ordered of how they should be consumed
     */
    public DataCollectionRepository(@NonNull DataSource<M>... orderedSources) {
        super(orderedSources);
    }

    public int getPaginationIndex() {
        return mPaginationIndex;
    }

    public void setPaginationIndex(int paginationIndex) {
        mPaginationIndex = paginationIndex;
    }

    /**
     * Public entry-point for getting items, no matter the source.
     * It follows the ordered of data sources that was provided in constructor.
     * It automatically falls back on the next source and only notifies listeners when needed:
     * upon first success or last failure (by lack of options left).
     *
     * @param context used for getting a reference to the sources, or network connectivity
     * @param callback where to asynchronously notify the caller of success/failure
     */
    public void getItems(final Context context, final DataSource.GetDataCallback<M> callback) {
        setBusy(true);
        boolean isOnline = NetworkHelper.isNetworkAvailable(context);
        Log.d(TAG, "Requesting item while " + (isOnline ? "online" : "offline"));
        mCollectionCallback = callback;
        final DataSource<M> firstSource = useNextSource();
        firstSource.getData(mDataCollectionLoopingCallback);
    }

    /**
     * Public entry-point for getting items, no matter the source.
     * It follows the ordered of data sources that was provided in constructor.
     * It automatically falls back on the next source and only notifies listeners when needed:
     * upon first success or last failure by lack of options left.
     *
     * @param context used for getting a reference to the sources, or network connectivity
     * @param page information about which subset of items to request (pagination feature)
     * @param callback where to asynchronously notify the caller of success/failure
     */
    public void getItems(final Context context, int page, final DataSource.GetDataCallback<M> callback) {
        setBusy(true);
        setPaginationIndex(page);
        boolean isOnline = NetworkHelper.isNetworkAvailable(context);
        Log.d(TAG, "Requesting items while " + (isOnline ? "online" : "offline"));
        mCollectionCallback = callback;
        final DataSource<M> firstSource = useNextSource();
        firstSource.getData(mDataCollectionLoopingCallback, page);
    }

    /** Internal callback that takes care of falling back on the next available source if necessary */
    private DataSource.GetDataCallback mDataCollectionLoopingCallback = new DataSource.GetDataCallback<M>() {
        @Override
        public void onSuccess(List<M> dataItems) {
            Log.i(TAG, "onSuccess(" + dataItems.size() + ")");
            if (shouldCacheData(getCurrentSource())) {
                mCachedDataSource.storeData(dataItems);
            }
            mConsumedSources.clear();
            mCollectionCallback.onSuccess(dataItems);
            // clear callback reference
            mCollectionCallback = null;
            setPaginationIndex(-1);
            setBusy(false);
        }

        @Override
        public void onFailure(Throwable throwable) {
            String sourceName = getCurrentSource() == null ? "" : getCurrentSource().getClass().getSimpleName();
            if (hasMoreSources()) {
                Log.w(TAG, "GetData Failed from source " + sourceName);
                Log.w(TAG, sourceName + ": Reason - " + throwable.getMessage());
                if (getPaginationIndex() < 0) {
                    useNextSource().getData(mDataCollectionLoopingCallback);
                } else {
                    useNextSource().getData(mDataCollectionLoopingCallback, getPaginationIndex());
                }
            } else {
                Log.e(TAG, "GetData Failed from source " + sourceName);
                Log.e(TAG, sourceName + ": Reason - " + throwable.getMessage());
                mCollectionCallback.onFailure(throwable);
                // clear data related to this run of getItems
                mConsumedSources.clear();
                mCollectionCallback = null;
                setPaginationIndex(-1);
                setBusy(false);
            }
        }

        @Override
        public void onDataMiss() {
            String sourceName = getCurrentSource() == null ? "" : getCurrentSource().getClass().getSimpleName();
            if (hasMoreSources()) {
                Log.w(TAG, "GetData Missed from source " + sourceName);
                if (getPaginationIndex() < 0) {
                    useNextSource().getData(mDataCollectionLoopingCallback);
                } else {
                    useNextSource().getData(mDataCollectionLoopingCallback, getPaginationIndex());
                }
            } else {
                Log.e(TAG, "GetData Missed from source " + sourceName);
                mCollectionCallback.onDataMiss();
                // clear data related to this run of getItems
                mConsumedSources.clear();
                mCollectionCallback = null;
                setPaginationIndex(-1);
                setBusy(false);
            }
        }
    };
}
