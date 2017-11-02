package nl.splendo.assignment.posytive.data.local;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.GsonBuilder;
import nl.splendo.assignment.posytive.data.DataSource;
import nl.splendo.assignment.posytive.data.models.responses.ListResponse;
import nl.splendo.assignment.posytive.helpers.mappers.ModelEmbeddedDataMapper;
import nl.splendo.assignment.posytive.mvp.ModelMVP;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The DataSource fetching data from embedded assets.
 * Assumption: this file is not expected to change
 *
 * This gives the option to show something on the UI, even when the first time use of the app is offline
 * It parses data on a different thread, returning data via callbacks on the main UI thread
 */
public class LocalDataSource<M extends BaseModel & ModelMVP> extends DataSource<M> implements DataSource.Expensive {

    /** Used for logging */
    public static final String TAG = "LocalDataSource";

    /** Context is needed to get a hold of the Assets folder */
    private Context mContext;

    public LocalDataSource(Class<M> modelClass, Context context) {
        super(modelClass);
        mContext = context;
    }

    @Override
    public @Nullable
    M getData(final GetDataCallback<M> callback) {
        Log.i(TAG, "Get Data! - list of " + getModelType());
        mThreadExecutor.execute(() -> {
            String filename = getAssetFilename();
            if (StringUtils.isNotEmpty(filename)) {
                String jsonString = loadJSONFromAsset(filename);
                final List<M> items = processJson(jsonString);
                if (items != null) {
                    Log.i(TAG, String.format("Retrieved #%d items from embedded Json", items.size()));
                    mUiThread.post(() -> callback.onSuccess(items));
                    return;
                }
            }
            mUiThread.post(() -> callback.onDataMiss());
        });
        return null;
    }

    /**
     * Parses a raw json string into the list of data needed
     */
    private List<M> processJson(String jsonString) {
        if (StringUtils.isNotEmpty(jsonString)) {
            ModelEmbeddedDataMapper modelResponseMapper = ModelEmbeddedDataMapper.getByTag(getModelType());
            if (modelResponseMapper != null) {
                ListResponse<M> parsed = new GsonBuilder().create().fromJson(jsonString, modelResponseMapper.getJsonRepresentationClass());
                return parsed.getDataAsList();
            }
        }
        return null;
    }

    @Override
    public M getData(GetDataCallback<M> callback, int page) {
        Log.w(TAG, "LocalDataSource() cannot be paginated, call normal getter");
        return getData(callback);
    }

    @Override
    public M getDataById(final String id, final GetItemCallback<M> callback) {
        String failureReason = "[NOT IMPLEMENTED] Local data source cannot be filtered by ID (" + id + ")";
        Throwable sorry = new Throwable(failureReason);
        mUiThread.post(() -> callback.onFailure(sorry));
        return null;
    }

    /**
     * Based on the MVP Model used, provides the path to a local embedded file.
     * (Is not mandatory to embed a file and a path for each type)
     * (Useful for Debugging and testing)
     *
     * @return the relative path of the file from the asset folder, or null if type not supported
     */
    private @Nullable
    String getAssetFilename () {
	ModelEmbeddedDataMapper modelPathMapper = ModelEmbeddedDataMapper.getByTag(getModelType());
	if (modelPathMapper != null) return modelPathMapper.getEmbeddedDataPath();
	return null;
    }

    /**
     * Fetch the data according to Model type this class is initialized with.
     * Such model should be parsable from JSON, described in the String returned
     *
     * @param filename the name of the file, or relative path starting from the asset folder
     * @return the file content in a String, ready to be parsed
     */
    private String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream stream = mContext.getAssets().open(filename);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ioe) {
            Log.w(TAG, "Error loading the embedded file: " + ioe.getLocalizedMessage());
            return null;
        }
        return json;
    }

}
