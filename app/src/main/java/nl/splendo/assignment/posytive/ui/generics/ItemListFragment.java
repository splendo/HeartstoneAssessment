package nl.splendo.assignment.posytive.ui.generics;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nl.splendo.assignment.posytive.R;
import nl.splendo.assignment.posytive.helpers.PaginatedRecyclerViewScrollListener;
import nl.splendo.assignment.posytive.helpers.mappers.ModelTypeMapper;
import nl.splendo.assignment.posytive.mvp.BaseView;
import nl.splendo.assignment.posytive.mvp.bindings.GenericMVPBinding;
import nl.splendo.assignment.posytive.presenters.generics.ItemListPresenter;
import nl.splendo.assignment.posytive.ui.generics.ItemsRecyclerAdapter.ViewHolder;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import spork.Spork;
import spork.android.BindResource;
import spork.android.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * The generic Fragment that receives data from its {@link GenericMVPBinding.Presenter} and
 * renders a list of items. Also handles user actions, such as selecting an item,
 * which are passed it to its Presenter.
 *
 * Read M as the Model (i.e. Card)
 * Read V as the MVP view, this class or extenders
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ItemListFragment<V extends GenericMVPBinding.ListerView, M extends BaseModel & GenericMVPBinding.ListableModel>
        extends BaseView implements GenericMVPBinding.PaginatedListerView {

    /** Used for logging */
    protected static final String TAG = "ItemListFragment";

    /** Bundle Key to pass to this view info about the type of "items to list/presenter config" */
    public static final String KEY_LISTABLE_MODEL_TYPE = "key_model_type";

    /** Bundle Key to pass to this view info about the type of "items to list/presenter config" */
    public static final String KEY_ITEM_ID = "key_item_id";

    /** Bundle Key to pass to this view info about the type of "items to list/presenter config" */
    public static final String KEY_ITEM = "key_item";

    /** Constants indicating the page to start with. API is 1-based, so 1 */
    public static final int STARTING_PAGE_INDEX = 1;

    @BindView(R.id.items_lister) protected RecyclerView vItemsRecycler;

    @BindView(R.id.empty_list) protected View vEmptyView;

    @Nullable
    @BindView(R.id.progress_layout) protected ViewGroup vProgressBar;

    /** The adapter for the generic item's list */
    private ItemsRecyclerAdapter<M, ViewHolder> mItemsAdapter;

    /** The Presenter that handles action on this View, and communicate with the Item(s) data model */
    protected ItemListPresenter<M, V> mPresenter;

    /** The type of model listed here. This is generic and decided at once upon initialization */
    protected Class<M> mDataClass;

    /** The readable type of model listed here. This is generic and provided in Bundle (Argument) */
    protected String mDataType;

    /** ScrollListener used to help the pagination feature, it will request new data when needed */
    protected PaginatedRecyclerViewScrollListener mPaginatedScrollListener;

    /** Boolean raised when the data should be reloaded, when the view is ready */
    protected  boolean mShouldReload = false;

    /** Reference to layout manager used, so its state can be saved/restored (in mListState) */
    private LinearLayoutManager mLinearLayoutManager;

    /** Where to save the state of list (scroll position above all) for restoring later */
    private Parcelable mListState;

    /** Where to save the items listed for restoring later */
    private List<M> mItemsCache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args == null || !(args.containsKey(KEY_LISTABLE_MODEL_TYPE))) {
            Log.d(TAG, "Not sure what to list here.. Bye!");
        } else {
            mDataType = args.getString(KEY_LISTABLE_MODEL_TYPE);
            ModelTypeMapper mpMapper = ModelTypeMapper.getByTag(mDataType);
            mDataClass = mpMapper.getTypeClass();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Spork.bind(this);

        setupRecyclerView(true);

        // if other views are extending this fragment, they might have set their own presenter
        // if so, the presenter is compatible with this view, so comply and skip this step
        if (mPresenter == null && mDataClass != null) {
            setPresenter(new ItemListPresenter<>((V) this, mDataClass));
        }

        if (getItemsAdapter() == null) {
            setItemsAdapter(new ItemsRecyclerAdapter<M, ViewHolder>(mPresenter, new ArrayList<>()));
        }
    }

    /**
     * Groups the action needed to setup the RecyclerView, both for behaviour and visuals
     *
     * @param usePagination true if the data should be loaded in chunks (fixed pagination size)
     */
    protected void setupRecyclerView(boolean usePagination) {

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        vItemsRecycler.setLayoutManager(mLinearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(vItemsRecycler.getContext(),
                mLinearLayoutManager.getOrientation());
        vItemsRecycler.addItemDecoration(dividerItemDecoration);

        if (usePagination) {
            mPaginatedScrollListener = new PaginatedRecyclerViewScrollListener(mLinearLayoutManager,
                    STARTING_PAGE_INDEX) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    mPresenter.getData(getContext(), page);
                }
            };

            vItemsRecycler.addOnScrollListener(mPaginatedScrollListener);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, TAG + " is active");
        mPresenter.onViewActive((V) this);
        if (mShouldReload) {
            mShouldReload = false;
            reload();
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, TAG + " is inactive");
        mPresenter.onViewInactive();
        if (mLinearLayoutManager != null) mListState = mLinearLayoutManager.onSaveInstanceState();
        mItemsCache = mItemsAdapter.mItems;
        super.onPause();
    }

    @Override
    public void setProgressBar(boolean show) {
        if (vProgressBar != null) {
            if (show) {
                vProgressBar.setVisibility(VISIBLE);
            } else {
                vProgressBar.setVisibility(GONE);
            }
        }
    }

    @Override
    public void setTitle(String title) {
        mActivityInteractionHelper.changeToolBarTitle(title);
    }

    @Override
    public void showItemDetails(GenericMVPBinding.ListableModel which) {
        showToastMessage("Selected " + which.getTitle());
    }

    @Override
    public <T extends BaseModel & GenericMVPBinding.ListableModel> void showItems(List<T> items) {
        boolean hadItems = getItemsAdapter().getItemCount() > 0;
        boolean hasItems = hadItems || (items != null && ( + items.size() > 0));
        vEmptyView.setVisibility(hasItems ? GONE : VISIBLE);
        vItemsRecycler.setVisibility(hasItems ? VISIBLE : GONE);
        if (items != null) getItemsAdapter().addAll(items);
    }

    protected ItemsRecyclerAdapter getItemsAdapter() {
        return mItemsAdapter;
    }

    protected void setItemsAdapter(ItemsRecyclerAdapter itemsAdapter) {
        mItemsAdapter = itemsAdapter;
        if (vItemsRecycler != null) vItemsRecycler.setAdapter(mItemsAdapter);
    }

    @Override
    public <V extends GenericMVPBinding.ListerView> void setPresenter(GenericMVPBinding.ListPresenter<V> presenter) {
        mPresenter = (ItemListPresenter) presenter;
    }

    @Override
    public <V extends GenericMVPBinding.View> void setPresenter(GenericMVPBinding.Presenter<V> presenter) {
        if (presenter instanceof GenericMVPBinding.ListPresenter) {
            setPresenter((GenericMVPBinding.ListPresenter) presenter);
        }
    }

    @Override
    public void reload() {
        Log.w(TAG, "reload");
        if (isVisible()) {
            if (mItemsCache != null) {
                restore();
            } else {
                setupRecyclerView(true);
                getItemsAdapter().clear();
                setItemsAdapter(getItemsAdapter());

                mPresenter.getData(getContext(), STARTING_PAGE_INDEX);
            }
        } else {
            mShouldReload = true;
        }
    }

    /**
     * Used to restore data and position of the scroll between short simple UX interactions
     * (i.e. screen rotation or back presses)
     */
    private void restore() {
        boolean hasChachedItems = mItemsCache != null;
        Log.i(TAG, "restore view: has items cached = " + hasChachedItems);
        if (hasChachedItems && mItemsAdapter != null && mItemsAdapter.getItemCount() == 0) {
            Log.v(TAG, "restore view: current items displayed = " + mItemsAdapter.getItemCount());
            showItems(mItemsCache);
            mItemsCache = null;
        }

        if (vItemsRecycler.getAdapter() == null) vItemsRecycler.setAdapter(mItemsAdapter);

        if (mLinearLayoutManager != null) mLinearLayoutManager.onRestoreInstanceState(mListState);
    }

    @Override
    public void onPaginatedItemsAllLoaded() {
        Log.e(TAG, "onPaginatedItemsAllLoaded() reached end of pagination for this collection");
        mPaginatedScrollListener.setIsEnabled(false);
    }
}