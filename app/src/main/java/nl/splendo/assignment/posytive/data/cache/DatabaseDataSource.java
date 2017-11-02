package nl.splendo.assignment.posytive.data.cache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import nl.splendo.assignment.posytive.data.DataSource;
import nl.splendo.assignment.posytive.helpers.db.LocalDatabase;
import nl.splendo.assignment.posytive.mvp.ModelMVP;
import nl.splendo.assignment.posytive.mvp.bindings.GenericMVPBinding;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

import static nl.splendo.assignment.posytive.presenters.generics.ItemListPresenter.PAGINATION_SIZE;

/**
 * The DataSource fetching data from previously cached data (in Database).
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class DatabaseDataSource<M extends BaseModel & ModelMVP>
        extends DataSource<M>
        implements DataSource.Writable<M> {

    /** Used for logging */
    public static final String TAG = "[DB]CachedDataSource";

    /** Reference to the class that helps storing the items, the DB used via DBFlow lib */
    private DatabaseDefinition mStorage;

    public DatabaseDataSource(Class<M> modelClass, Context context) {
        super(modelClass);
        FlowManager.init(context);
        mStorage = FlowManager.getDatabase(LocalDatabase.class);
    }

    @Override
    public @Nullable
    M getData(final GetDataCallback<M> callback) {
        Log.d(TAG, "getDatabaseCachedData(" + getModelType() + ")");

        mThreadExecutor.execute(() -> {
            final List<M> data = SQLite.select()
                    .from(mDataType)
                    .queryList();


            if (data.size() > 0) {
                Log.i(TAG, "Found data cached in DATABASE (" + getModelType() + ")");
                for (M item : data) item.loadAttributes(mStorage);
                mUiThread.post(() -> callback.onSuccess(data));
            } else {
                Log.w(TAG, "No " + getModelType() + " items cached in DATABASE");
                mUiThread.post(() -> callback.onDataMiss());
            }
        });
        return null;
    }

    @Override
    public @Nullable
    M getData(final GetDataCallback<M> callback, int page) {
        Log.d(TAG, "getDatabaseCachedData(" + getModelType() + ")");

        mThreadExecutor.execute(() -> {
            final List<M> data = SQLite.select()
                    .from(mDataType)
                    .limit(PAGINATION_SIZE)
                    .offset((page - 1) * PAGINATION_SIZE)
                    .queryList();


            if (data.size() > 0) {
                Log.i(TAG, "Found data cached in DATABASE (" + getModelType() + ")");
                for (M item : data) item.loadAttributes(mStorage);
                mUiThread.post(() -> callback.onSuccess(data));
            } else {
                Log.w(TAG, "No " + getModelType() + " items cached in DATABASE");
                mUiThread.post(() -> callback.onDataMiss());
            }
        });
        return null;
    }

    /**
     * Stores the list of items provided in a permanent storage, available in future runs
     * It uses the DB for the actual storing, and the definition of table/columns/relations is
     * annotated on the models themselves.
     *
     * @param data a list of MVP models. They should extend {@link BaseModel} to have DB powers
     */
    @Override
    public void storeData(final List<M> data) {
        // no need to run it on different thread: call already async
        Transaction transaction = mStorage.beginTransactionAsync(
                databaseWrapper -> {
                    for (M item : data) saveItemKeepState(item);
                }).build();
        transaction.execute();
    }

    /**
     * Updates the item in DB while keeps the state of favorite
     *
     * @param item an item possibly containing updated fields, but missing favorite info
     * @return the final item
     */
    protected M saveItemKeepState(M item) {
        if (item.exists() && item instanceof GenericMVPBinding.FavoritableModel) {
            M newItem = (M) item.makeCopy();
            item.load();
            boolean wasFavorite = ((GenericMVPBinding.FavoritableModel) item).isFavorite();
            ((GenericMVPBinding.FavoritableModel) newItem).setFavorite(wasFavorite);
            newItem.update();
            newItem.storeAttributes(mStorage);
            return newItem;
        }
        // otherwise, if it is a new item or doesn't need state saving
        // just save
        item.save();
        item.storeAttributes(mStorage);
        return item;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public M getDataById(String id, GetItemCallback<M> callback) {
        Log.d(TAG, "getDatabaseCachedData(" + getModelType() + ") @ " + id);

        mThreadExecutor.execute(() -> {
            try {
                Property idColumn = mDataType.newInstance().getPrimaryColumn();
                final M data = SQLite.select()
                        .from(mDataType)
                        .where(idColumn.eq(id))
                        .querySingle();

                if (data != null) {
                    data.loadAttributes(mStorage);
                    Log.i(TAG, "Found data cached in DATABASE (" + getModelType() + "): " + data.getTitle());
                    mUiThread.post(() -> callback.onSuccess(data));
                } else {
                    Log.w(TAG, "No " + getModelType() + " item cached in DATABASE by the id " + id);
                    mUiThread.post(() -> callback.onDataMiss());
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return null;
    }

    /**
     * Stores the item provided in a permanent storage, available in future runs
     * It uses the DB for the actual storing, and the definition of table/columns/relations is
     * annotated on the models themselves.
     *
     * @param item the MVP model to persist. It should extend {@link BaseModel} to have DB powers
     */
    @Override
    public void storeItem(M item) {
        // no need to run it on different thread: call already async
        Transaction transaction = mStorage.beginTransactionAsync(
                databaseWrapper -> item.save()
        ).build();
        transaction.execute();
    }

}
