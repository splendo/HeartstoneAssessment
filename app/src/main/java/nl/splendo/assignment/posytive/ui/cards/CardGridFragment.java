package nl.splendo.assignment.posytive.ui.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.parceler.Parcels;

import nl.splendo.assignment.posytive.R;
import nl.splendo.assignment.posytive.data.Types;
import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.helpers.PaginatedRecyclerViewScrollListener;
import nl.splendo.assignment.posytive.mvp.bindings.CardsBinding;
import nl.splendo.assignment.posytive.mvp.bindings.GenericMVPBinding;
import nl.splendo.assignment.posytive.presenters.CardsPresenter;
import nl.splendo.assignment.posytive.ui.generics.ItemListFragment;

import java.util.ArrayList;
import java.util.List;

import spork.Spork;
import spork.android.BindResource;


/**
 * The {@see Fragment} that receives card data from its {@link CardsBinding.ListPresenter} and
 * displays a list of card items. Also handles user actions, such as selecting a card,
 * which are passed it to its presenter.
 */
@SuppressWarnings("unchecked")
public class CardGridFragment
        extends ItemListFragment<CardsBinding.ListerView, Card>
        implements CardsBinding.ListerView {

    /** Used for logging */
    public static final String TAG = "CardsFragment";

    /** Number of columns in the grid displayed on UI */
    // TODO: could be an integer depending on orientation in dimens.xml
    public static final int GRID_COLUMN_SPAN = 3;

    /** Number of columns in the grid displayed on UI */
    // TODO: could be an integer depending on orientation in dimens.xml
    public static final int GRID_ROW_SPAN = 3;

    @BindResource(R.string.data_loading)
    protected String mCardsLoadingLabel;

    /** The adapter for the cards' list */
    private CardsRecyclerAdapter mCardsAdapter;

    /** The Presenter that handles action on this View, and communicate with the Card(s) data model */
    private CardsBinding.ListPresenter mCardsPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "CardFragment created");
        mDataClass = Card.class;
        mDataType = Types.Item.CARD;
        mCardsPresenter = new CardsPresenter(this);
        setPresenter(mCardsPresenter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Spork.bind(this);

        setupRecyclerView(true);

        mCardsAdapter = new CardsRecyclerAdapter(mCardsPresenter, new ArrayList<>());
        setItemsAdapter(mCardsAdapter);
    }

    /**
     * Groups the action needed to setup the RecyclerView, both for behaviour and visuals
     *
     * @param usePagination true if the data should be loaded in chunks (fixed pagination size)
     */
    @Override
    protected void setupRecyclerView(boolean usePagination) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), GRID_COLUMN_SPAN, LinearLayoutManager.VERTICAL, false);
        vItemsRecycler.setLayoutManager(gridLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(vItemsRecycler.getContext(),
                gridLayoutManager.getOrientation());
        vItemsRecycler.addItemDecoration(dividerItemDecoration);

        if (usePagination) {
            mPaginatedScrollListener = new PaginatedRecyclerViewScrollListener(gridLayoutManager, STARTING_PAGE_INDEX) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    mPresenter.getData(getContext(), page);
                }
            };

            vItemsRecycler.addOnScrollListener(mPaginatedScrollListener);
        }
    }

    public void showCards(List<Card> cards) {
        Log.i(TAG, "showCards(" + cards.size() + ")");
        showItems(cards);
    }

    @Override
    public void showCardDetails(Card which) {
        showItemDetails(which);
    }

    @Override
    public void showItemDetails(GenericMVPBinding.ListableModel which) {
        Bundle extraInfo = DetailsActivity.newIntentData(which.getId(), (Card) which);
        mActivityInteractionHelper.changeActivity(DetailsActivity.class, extraInfo);
    }
}