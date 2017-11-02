package nl.splendo.assignment.posytive.presenters.generics;

import android.content.Context;
import android.util.Log;

import nl.splendo.assignment.posytive.data.DataSource;
import nl.splendo.assignment.posytive.data.cache.DatabaseDataSource;
import nl.splendo.assignment.posytive.data.remote.RemoteDataSource;
import nl.splendo.assignment.posytive.data.repositories.DataCollectionRepository;
import nl.splendo.assignment.posytive.helpers.mappers.ModelRemoteDataMapper;
import nl.splendo.assignment.posytive.mvp.BasePresenter;
import nl.splendo.assignment.posytive.mvp.bindings.GenericMVPBinding;
import nl.splendo.assignment.posytive.ui.generics.ItemListFragment;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * The Presenter that fetches data by calling {@link DataCollectionRepository} and delivers the data to its View.
 * The presenter also calls other relevant methods of its View such as for showing/hiding progress
 * and for showing Toast messages.
 * The Presenter subscribes to its View lifecycle by allowing the View to call the Presenter's
 * {@see #onViewActive(Object)} and {@link #onViewInactive()} to reference/unreference its View.
 * This allows its View to be GCed and prevents memory leaks.
 * The Presenter also checks if its View is active before calling any of its methods.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ItemListPresenter<M extends BaseModel & GenericMVPBinding.ListableModel, V extends GenericMVPBinding.ListerView>
        extends BasePresenter<V>
        implements GenericMVPBinding.ListPresenter<V> {

    /** Used for logging */
    public static final String TAG = "ItemListPresenter";

    /** Represents the amount of item to list before requesting new ones */
    public static final int PAGINATION_SIZE = 100;

    /** Main interactor with all the possible data sources that fetches known models */
    protected DataCollectionRepository<M> mDataRepository;

    /** Used to match data sources of the same type when mixed in DataRepository<M> */
    protected Class<M> mDataClass;

    /** Recognizer of Data class */
    protected String mDataType;

    /** Flag used to check if the presenter is ready, allows to run init() code only once */
    protected boolean mIsInitialized = false;

    /** Flag raised when the call for data is using pagination */
    protected boolean mIsDataPaginated = true;

    /**
     * Final listener that asynchronously gathers the data, or the error, from the model.
     * Contains the logic for what to do next.
     */
    protected final DataSource.GetDataCallback mDataListener = new DataSource.GetDataCallback() {

        @Override
        public void onSuccess(List dataItems) {
            Log.d(TAG, "onSuccess(" + dataItems.size() + ")");
            if (mView != null) {
                mMainUiThread.post(() -> {
                    if (mIsDataPaginated && dataItems.size() != PAGINATION_SIZE) {
                        // got less items than expected, pagination likely ended
                        ((GenericMVPBinding.PaginatedListerView) mView).onPaginatedItemsAllLoaded();
                        mIsDataPaginated = false;
                    }
                    mView.showItems(dataItems);
                    mView.setProgressBar(false);
                });
            }
        }

        @Override
        public void onFailure(Throwable throwable) {
            Log.d(TAG, "onFailure(" + throwable.getMessage() + ")");
            if (mView != null) {
                mMainUiThread.post(() -> {
                    if (mIsDataPaginated) {
                        // got less items than expected, pagination likely ended
                        ((GenericMVPBinding.PaginatedListerView) mView).onPaginatedItemsAllLoaded();
                        mIsDataPaginated = false;
                    }
                    mView.setProgressBar(false);
                });
                mView.showToastMessage(throwable.getMessage());
            }
        }

        @Override
        public void onDataMiss() {
            Log.d(TAG, "onDataMiss()");
            if (mView != null) {
                mMainUiThread.post(() -> {
                    if (mIsDataPaginated) {
                        // got less items than expected, pagination likely ended
                        ((GenericMVPBinding.PaginatedListerView) mView).onPaginatedItemsAllLoaded();
                        mIsDataPaginated = false;
                    }
                    // hide loading and show empty list page
                    mView.setProgressBar(false);
                    mView.showItems(null);
                });
            }
        }
    };

    public ItemListPresenter(V view, Class<M> dataClass) {
        super(view);
        mDataClass = dataClass;
        mDataType = DataSource.getModelType(dataClass);
        initRepo();
        init();
    }

    /**
     * Allows to initialize the DataRepository<>
     */
    protected void initRepo() {
        setDataRepository(mContext, mDataClass);
    }

    /**
     * Beginning of presenter logic
     */
    protected void init() {
        // this presenter is generic for a lister, load data immediately
        if (mIsDataPaginated) {
            Log.i(TAG, "init(): start at page " + ItemListFragment.STARTING_PAGE_INDEX);
            getData(mContext, ItemListFragment.STARTING_PAGE_INDEX);
        } else {
            Log.i(TAG, "init()");
            getData(mContext);
        }
        getView().setTitle("Cards");
        mIsInitialized = true;
    }

    @Override
    public void onViewActive(V view) {
        super.onViewActive(view);
        if (!mIsInitialized && mDataRepository != null && !mDataRepository.isBusy()) init();
    }

    /**
     * Get all Data of this type, asynchronously, via DataRepository.
     *
     * @param context needed by the DataRepository
     */
    public void getData(Context context) {
        Log.e(TAG, "get ALL data");
        if (mView == null) {
            return;
        }
        mIsDataPaginated = false;

        Log.d(TAG, "getData(" + mDataRepository + ")");

        mMainUiThread.post(() -> getView().setProgressBar(true));

        mThreadExecutor.execute(() -> mDataRepository.getItems(context, mDataListener));
    }

    /**
     * Get all Data of this type, asynchronously, via DataRepository.
     * This method fetches only part of the data, in a paginated way
     *
     * @param context needed by the DataRepository
     */
    public void getData(Context context, int page) {
        if (mView == null) {
            return;
        }
        mIsDataPaginated = true;
        Log.d(TAG, "getData(" + mDataRepository + ") page " + page);

        mMainUiThread.post(() -> getView().setProgressBar(true));

        mThreadExecutor.execute(() -> mDataRepository.getItems(context, page, mDataListener));
    }

    @Override
    public <M extends BaseModel & GenericMVPBinding.Model> void setDataRepository(Context context, Class<M> dataType) {
        DatabaseDataSource<M> dbData = new DatabaseDataSource<>(dataType, context);

        // check if api data is available, but always prefer DB for this presenter
        ModelRemoteDataMapper remoteDataMapper = ModelRemoteDataMapper.getByClass(dataType);
        if (remoteDataMapper != null) {
            RemoteDataSource<M> apiData = remoteDataMapper.getDataSource(context);
            mDataRepository = new DataCollectionRepository(dbData, apiData);
        } else {
            mDataRepository = new DataCollectionRepository(dbData);
        }
    }

    @Override
    public <M extends BaseModel & GenericMVPBinding.ListableModel> void onItemSelected(Context context, M which) {
        getView().showItemDetails(which);
    }
}
