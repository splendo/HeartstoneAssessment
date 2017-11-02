package nl.splendo.assignment.posytive.data.repositories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import nl.splendo.assignment.posytive.data.DataSource;
import nl.splendo.assignment.posytive.helpers.NetworkHelper;
import nl.splendo.assignment.posytive.mvp.ModelMVP;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * The entry point to deal with all possible data sources such as {@see RemoteDataSource},
 * {@see LocalDataSource} or {@see DatabaseDataSource}
 * Delegates the work to them depending on conditions such as network availability or failures.
 *
 * This particular repository defines and deals with sources that can fetch a single item, by id.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class DataItemRepository<M extends BaseModel & ModelMVP> extends BaseDataRepository<M> {

    /** Used for logging */
    public static final String TAG = "DataItemRepository";

    /** Reference to the external callback to notify about a single item, valid while the data is fetched */
    private DataSource.GetItemCallback<M> mCallback;

    /** Reference to the id of the item to find, valid while the data is fetched */
    private String mItemId;

    public DataItemRepository(@NonNull DataSource<M>... orderedSources) {
        super(orderedSources);
    }

    public void getDataById(final Context context, final String id, final DataSource.GetItemCallback<M> callback) {
        setBusy(true);
        mItemId = id;
        boolean isOnline = NetworkHelper.isNetworkAvailable(context);
        Log.d(TAG, "Requesting item while " + (isOnline ? "online" : "offline"));
        mCallback = callback;
        final DataSource<M> firstSource = useNextSource();
        firstSource.getDataById(id, mDataLoopingCallback);
    }

    /** Internal callback that takes care of falling back on the next available source if necessary */
    private final DataSource.GetItemCallback mDataLoopingCallback = new DataSource.GetItemCallback<M>() {

        @Override
        public void onSuccess(M item) {
            Log.i(TAG, "onSuccess(" + item + ")");
            if (shouldCacheData(getCurrentSource())) {
                mCachedDataSource.storeItem(item);
            }
            mConsumedSources.clear();
            mCallback.onSuccess(item);
            // clear references
            mCallback = null;
            mItemId = null;
            setBusy(false);
        }

        @Override
        public void onFailure(Throwable throwable) {
            String sourceName = getCurrentSource() == null ? "" : getCurrentSource().getClass().getSimpleName();
            if (hasMoreSources()) {
                Log.w(TAG, "GetData Failed from source " + sourceName);
                Log.w(TAG, sourceName + ": Reason - " + throwable.getMessage());
                useNextSource().getDataById(mItemId, mDataLoopingCallback);
            } else {
                Log.e(TAG, "GetData Failed from source " + sourceName);
                Log.e(TAG, sourceName + ": Reason - " + throwable.getMessage());
                mCallback.onFailure(throwable);
                // clear data related to this run of getItems
                mConsumedSources.clear();
                mCallback = null;
                mItemId = null;
                setBusy(false);
            }
        }

        @Override
        public void onDataMiss() {
            String sourceName = getCurrentSource() == null ? "" : getCurrentSource().getClass().getSimpleName();
            if (hasMoreSources()) {
                Log.w(TAG, "GetData Missed from source " + sourceName);
                useNextSource().getDataById(mItemId, mDataLoopingCallback);
            } else {
                Log.e(TAG, "GetData Missed from source " + sourceName);
                mCallback.onDataMiss();
                // clear data related to this run of getItems
                mConsumedSources.clear();
                mCallback = null;
                mItemId = null;
                setBusy(false);
            }
        }
    };
}
