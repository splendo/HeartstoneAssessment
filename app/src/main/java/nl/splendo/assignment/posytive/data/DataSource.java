package nl.splendo.assignment.posytive.data;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import nl.splendo.assignment.posytive.helpers.threading.MainUiThread;
import nl.splendo.assignment.posytive.helpers.threading.ThreadExecutor;
import nl.splendo.assignment.posytive.mvp.ModelMVP;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * A generic data source: defines the possible calls but doesn't implement them.
 * Forces extending classes to provide the correct data, regardless of the source
 * Every data source possibly used in this app must extend this base class.
 * For convenience, this class also holds the definition of some interfaces that some DataSource(s)
 * might want to implement as, for example, for enabling the caching feature
 */
public abstract class DataSource<M extends BaseModel & ModelMVP> {

    /** Used for logging */
    private static final String TAG = "(Generic)DataSource";

    /** Used for executing on the UI thread */
    protected MainUiThread mUiThread;

    /** used for executing on a background thread */
    protected ThreadExecutor mThreadExecutor;

    /** Used to match data sources of the same type when mixed in DataRepository<M> */
    protected Class<M> mDataType;

    /** String caching of mDataType, not to recalculate at every call */
    private String mDataTypeString;

    /**
     * Constructor
     *
     * @param modelClass the Class of the model that this DataSource handles
     */
    public DataSource(Class<M> modelClass) {
        mUiThread = MainUiThread.getInstance();
        mThreadExecutor = ThreadExecutor.getInstance();
        mDataType = modelClass;
    }

    @Nullable
    public String getModelType() {
        if (!TextUtils.isEmpty(mDataTypeString)) return mDataTypeString;
        mDataTypeString = getModelType(mDataType);
        return mDataTypeString;
    }

    @Nullable
    public static <M extends BaseModel & ModelMVP> String getModelType(Class<M> mDataType) {
        try {
            return mDataType.newInstance().getModelType();
        } catch (InstantiationException e) {
            Log.e(TAG, "NOPE! no instance");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.e(TAG, "NOPE! illegal");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Generic simple callback for asynchronously receiving a list of models
     * CM stands for Callback Model, guaranteed to be Json parsable and DB ready by restrictions
     */
    public interface GetDataCallback<CM extends BaseModel & ModelMVP> {

        void onSuccess(List<CM> dataItems);

        void onFailure(Throwable throwable);

        void onDataMiss();
    }

    /**
     * Generic simple callback for asynchronously receiving a single item
     * CM stands for Callback Model, guaranteed to be Json parsable and DB ready by restrictions
     */
    public interface GetItemCallback<CM extends BaseModel & ModelMVP> {

        void onSuccess(CM item);

        void onFailure(Throwable throwable);

        void onDataMiss();
    }

    /**
     * Gets the list of compatible items in an asynchronous way.
     * Forces callback type and model to match
     *
     * @param callback the implementer of the interface that handles the result
     */
    public abstract M getData(GetDataCallback<M> callback);

    /**
     * Gets the list of compatible items in an asynchronous way, but request only a specific range
     * Forces callback type and model to match
     *
     * @param callback the implementer of the interface that handles the result
     * @param page the required page index, to allow pagination of results
     */
    public abstract M getData(GetDataCallback<M> callback, int page);

    /**
     * Gets the specified item in an asynchronous way.
     * Forces callback type and model to match
     *
     * @param id       the id of the item, to recognize it among the others in the collection
     * @param callback the implementer of the interface that handles the result
     */
    public abstract M getDataById(String id, GetItemCallback<M> callback);

    /**
     * Used by DataSource(s) that can also store data, so they can be used as cache
     */
    public interface Writable<T> {


        /**
         * Stores the list of items provided in a permanent storage, available in future runs
         *
         * @param data a list of MVP models to persist.
         */
        void storeData(final List<T> data);

        void storeItem(final T item);
    }

    /**
     * Used by DataSource(s) that should cache their data if possible, as it's an expensive source.
     * (either because it takes the longest time to complete, or because it might need costly a data
     * connection, or other reasons)
     * There is no method cause the interface is used as a recognizer of such DataSource(s), without
     * relying on the actual class or other mechanisms offered by the Java language
     */
    public interface Expensive {}
}
