package nl.splendo.assignment.posytive.data.remote;

import android.util.Log;

import nl.splendo.assignment.posytive.data.DataSource;
import nl.splendo.assignment.posytive.mvp.ModelMVP;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * The class for fetching data from Github on a background thread and returning data via
 * callbacks on the main UI thread
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class RemoteDataSource<M extends BaseModel & ModelMVP> extends DataSource<M> implements DataSource.Expensive {

    /** Used for logging */
    public static final String TAG = "RemoteDataSource";

    /** Name of the parameter in the query, indicates which page to load */
    public static final String QUERY_PARAM_PAGE_INDEX = "_page";

    /** Name of the parameter in the query, indicates how many items to load */
    public static final String QUERY_PARAM_PAGE_SIZE = "_limit";

    public RemoteDataSource(Class<M> modelClass) {
        super(modelClass);
    }

    @Override
    public abstract M getData(GetDataCallback<M> callback);

    @Override
    public M getData(GetDataCallback<M> callback, int page) {
        // TODO: implement if AppEngine backend is done and allows pagination
        Log.e(TAG, "[NOT IMPLEMENTED] CANNOT HANDLE getRemoteItems(" + getModelType() + "): page " + page);
        return null;
    }

    @Override
    public M getDataById(final String id, final GetItemCallback<M> callback) {
        // TODO: implement if AppEngine backend is done
        String failureReason = "[NOT IMPLEMENTED] Remote data source cannot be filtered by ID (" + id + ")";
        Throwable sorry = new Throwable(failureReason);
        mUiThread.post(() -> callback.onFailure(sorry));
        return null;
    }

    /**
     * Github raw fetching cannot support pagination, but there is no harm adding the query part anyway
     * This method generates that based on the page to get and the amount of items in that page.
     * It will be useful if the backend used supports it, and switched to it in gradle file
     *
     * @param page the index of the page to get, 1-based
     * @param paginationSize the size of each page
     *
     * @return the correct query string for the intended pagination, starting with '?'
     */
    String generatePaginatedQueryString(int page, int paginationSize) {
        return String.format("?%s=%d&%s=%s", QUERY_PARAM_PAGE_INDEX, page, QUERY_PARAM_PAGE_SIZE, paginationSize);
    }

}
