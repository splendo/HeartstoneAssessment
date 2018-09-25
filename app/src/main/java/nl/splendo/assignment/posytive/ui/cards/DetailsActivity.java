package nl.splendo.assignment.posytive.ui.cards;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import nl.splendo.assignment.posytive.R;
import nl.splendo.assignment.posytive.data.cache.DatabaseDataSource;
import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.helpers.LifecycleInteractor;
import nl.splendo.assignment.posytive.helpers.callbacks.GetCardsCallback;
import nl.splendo.assignment.posytive.ui.BaseActivity;
import spork.Spork;
import spork.android.BindLayout;
import spork.android.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static nl.splendo.assignment.posytive.ui.generics.ItemListFragment.KEY_ITEM;
import static nl.splendo.assignment.posytive.ui.generics.ItemListFragment.KEY_ITEM_ID;

/**
 * The container responsible for showing and destroying relevant (Detailed) Fragment, handling
 * previous/next navigation using the Fragment Pager and maintaining global state.
 */
@BindLayout(R.layout.activity_details)
public class DetailsActivity extends BaseActivity implements LifecycleInteractor {

    public static final String TAG = "DetailsActivity";

    @BindView(R.id.toolbar) private Toolbar vToolbar;

    @BindView(R.id.progress_layout) private View vLoading;

    @BindView(R.id.item_details_viewpager) private ViewPager vPager;

    private CardDetailViewPagerAdapter mViewPagerAdapter;

    private Card mInitialCard;

    private String mInitialCardId;

    public static Bundle newIntentData(String cardId, @Nullable Card card) {
        Bundle loadMeWith = new Bundle();
        loadMeWith.putString(KEY_ITEM_ID, cardId);
        if (card != null) loadMeWith.putParcelable(KEY_ITEM, Parcels.wrap(card));
        return loadMeWith;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spork.bind(this);
        setSupportActionBar(vToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        if (getIntent() != null && getIntent().getExtras() != null) {
            findInitialCardFromArgs(getIntent().getExtras());
        }
        loadData();
    }

    @Nullable private String findInitialCardFromArgs(@NonNull Bundle extra) {
        if (extra.containsKey(KEY_ITEM)) {
            mInitialCard = Parcels.unwrap(extra.getParcelable(KEY_ITEM));
            mInitialCardId = mInitialCard.getId();
        } else if (extra.containsKey(KEY_ITEM_ID)) {
            mInitialCardId = extra.getString(KEY_ITEM_ID);
        }
        return mInitialCardId;
    }

    private void loadData() {
        DatabaseDataSource<Card> dbData = new DatabaseDataSource<>(Card.class, this);
        vLoading.setVisibility(VISIBLE);
        dbData.getData(mDataListener);
    }

    private void setupViewPager(List<Card> filteredCards) {
        mViewPagerAdapter = new CardDetailViewPagerAdapter(getSupportFragmentManager(), filteredCards);
        vPager.setOffscreenPageLimit(3);
        vPager.setAdapter(mViewPagerAdapter);
        vPager.setCurrentItem(mViewPagerAdapter.getPositionOf(mInitialCard));
    }

    @Override
    protected @IdRes
    int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public void changeToolBarTitle(String title) {
        vToolbar.setTitle(title);
    }


    /**
     * Listener that asynchronously gathers the data, or the error, and setups the Ui when done
     */
    protected final GetCardsCallback mDataListener = new GetCardsCallback() {

        @Override
        public void onSuccess(List<Card> cards) {
            Log.d(TAG, "onSuccess(" + cards.size() + ")");
            setupViewPager(cards);
            vLoading.setVisibility(GONE);
        }

        @Override
        public void onFailure(Throwable throwable) {
            Log.d(TAG, "onFailure(" + throwable.getMessage() + ")");
            vLoading.setVisibility(GONE);
            if (mInitialCard != null) {
                List<Card> selectionOnlyList = new ArrayList<>();
                selectionOnlyList.add(mInitialCard);
                setupViewPager(selectionOnlyList);
            }
        }

        @Override
        public void onDataMiss() {
            Log.d(TAG, "onDataMiss()");
        }
    };
}
