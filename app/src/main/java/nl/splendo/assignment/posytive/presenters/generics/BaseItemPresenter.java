package nl.splendo.assignment.posytive.presenters.generics;

import android.content.Context;

import nl.splendo.assignment.posytive.data.DataSource;
import nl.splendo.assignment.posytive.data.cache.DatabaseDataSource;
import nl.splendo.assignment.posytive.data.remote.RemoteDataSource;
import nl.splendo.assignment.posytive.data.repositories.DataItemRepository;
import nl.splendo.assignment.posytive.helpers.mappers.ModelRemoteDataMapper;
import nl.splendo.assignment.posytive.mvp.BasePresenter;
import nl.splendo.assignment.posytive.mvp.bindings.GenericMVPBinding;
import com.raizlabs.android.dbflow.structure.BaseModel;


/**
 * The Presenter that fetches data by calling {@see DataCollectionRepository} and delivers the data to its View.
 * The presenter also calls other relevant methods of its View such as for showing/hiding progress
 * and for showing Toast messages.
 * The Presenter subscribes to its View lifecycle by allowing the View to call the Presenter's
 * {@see #onViewActive(Object)} and {@link #onViewInactive()} to reference/unreference its View.
 * This allows its View to be GCed and prevents memory leaks.
 * The Presenter also checks if its View is active before calling any of its methods.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class BaseItemPresenter<M extends BaseModel & GenericMVPBinding.ListableModel, V extends GenericMVPBinding.View>
        extends BasePresenter<V>
        implements GenericMVPBinding.Presenter<V> {

    /** Used for logging */
    public static final String TAG = "BaseItemPresenter";

    /** Main interactor with all the possible data sources that fetches known models */
    protected DataItemRepository<M> mDataRepository;

    /** Used to match data sources of the same type when mixed in DataRepository<M> */
    protected Class<M> mDataClass;

    /** Recognizer of Data class */
    protected String mDataType;

    public BaseItemPresenter(V view, Class<M> dataClass) {
        super(view);
        mDataClass = dataClass;
        mDataType = DataSource.getModelType(dataClass);
        initRepo();
    }

    /**
     * Allows to initialize the DataRepository<>
     */
    protected abstract void initRepo();

    /**
     * Beginning of presenter logic
     */
    public abstract void init();

    @Override
    public <M extends BaseModel & GenericMVPBinding.Model> void setDataRepository(Context context, Class<M> dataType) {
        DatabaseDataSource<M> dbData = new DatabaseDataSource<>(dataType, context);
        ModelRemoteDataMapper remoteDataMapper = ModelRemoteDataMapper.getByClass(dataType);
        if (remoteDataMapper != null) {
            RemoteDataSource<M> apiData = remoteDataMapper.getDataSource(context);
            mDataRepository = new DataItemRepository(dbData, apiData);
        } else {
            mDataRepository = new DataItemRepository(dbData);
        }
    }
}
