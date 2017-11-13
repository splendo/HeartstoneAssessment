package org.arnoid.heartstone;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import org.arnoid.heartstone.controller.DatabaseController;
import org.arnoid.heartstone.data.Card;
import org.arnoid.heartstone.data.util.CardsFilter;
import org.arnoid.heartstone.usecase.LoadCachedCardsListUseCase;
import org.arnoid.heartstone.usecase.PullRemoteCardsListAndSaveUseCase;
import org.arnoid.heartstone.view.list.CardListAdapter;
import org.arnoid.heartstone.view.list.CardsFilterAdapter;
import org.arnoid.heartstone.view.util.ViewStateSwitcher;

import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Displays list of Cards.
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, DrawerLayout.DrawerListener, Observer<PagedList<Card>> {

    private static final String STATE_RECYCLER_POSITION = "STATE_RECYCLER_POSITION";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rclrCardList;
    private ViewStateSwitcher viewStateSwitcher;
    private Disposable cardsSubscription;

    private CardListAdapter adapter;
    private GridLayoutManager rclrLayoutManager;
    private int lastKnownPostition;
    private ExpandableListView expListFilteringOptions;
    private DrawerLayout drawerLayout;

    private CardsFilter cardsFilter;
    private CardsFilterAdapter filterAdapter;

    private boolean isFilteringDisabled = false;
    private LiveData<PagedList<Card>> liveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            lastKnownPostition = savedInstanceState.getInt(STATE_RECYCLER_POSITION, -1);
        }
        drawerLayout = findViewById(R.id.drawer_layout);

        drawerLayout.addDrawerListener(this);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        filterAdapter = new CardsFilterAdapter(new CardsFilter());
        expListFilteringOptions = findViewById(R.id.explst_filtering_list);
        expListFilteringOptions.setAdapter(filterAdapter);

        rclrLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.column_count), GridLayoutManager.VERTICAL, false);

        rclrCardList = findViewById(R.id.rclr_card_list);
        rclrCardList.setLayoutManager(rclrLayoutManager);
        rclrCardList.setHasFixedSize(true);

        swipeRefreshLayout.setOnRefreshListener(this);

        viewStateSwitcher = ViewStateSwitcher.Builder.withView(findViewById(android.R.id.content))
                .content(rclrCardList)
                .empty(R.id.v_empty_state)
                .error(R.id.v_error_state)
                .build();

        viewStateSwitcher.content();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadLocalData();
    }

    private void loadLocalData() {
        disposeSubscription();

        swipeRefreshLayout.setRefreshing(true);

        cardsSubscription = subscribeForCards(
                procudeConfigFetchCompletable(false)
                        .flatMap(new Function<CardsFilter, Flowable<LiveData<PagedList<Card>>>>() {
                            @Override
                            public Flowable<LiveData<PagedList<Card>>> apply(CardsFilter cardsFilter) throws Exception {
                                LoadCachedCardsListUseCase loadCachedCardsListUseCase = new LoadCachedCardsListUseCase(HeartstoneApplication.database());
                                loadCachedCardsListUseCase.setInitialPosition(lastKnownPostition);
                                loadCachedCardsListUseCase.setFilter(cardsFilter);
                                return loadCachedCardsListUseCase.execute();
                            }
                        }));
    }

    @Override
    protected void onStop() {
        super.onStop();

        disposeSubscription();

        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        disposeSubscription();

        final DatabaseController database = HeartstoneApplication.database();

        cardsSubscription = subscribeForCards(
                new PullRemoteCardsListAndSaveUseCase(HeartstoneApplication.network(), database).execute()
                        .andThen(procudeConfigFetchCompletable(true))
                        .flatMap(new Function<CardsFilter, Flowable<LiveData<PagedList<Card>>>>() {
                            @Override
                            public Flowable<LiveData<PagedList<Card>>> apply(CardsFilter cardsFilter) throws Exception {
                                LoadCachedCardsListUseCase loadCachedCardsListUseCase = new LoadCachedCardsListUseCase(database);
                                loadCachedCardsListUseCase.setFilter(cardsFilter);
                                return loadCachedCardsListUseCase.execute();
                            }
                        })
        );

    }

    private void disposeSubscription() {
        if (cardsSubscription != null) {
            cardsSubscription.dispose();
        }

        if (liveData != null) {
            liveData.removeObserver(this);
        }
    }

    private void disableOptionsMenu() {
        isFilteringDisabled = true;
        invalidateOptionsMenu();
    }

    private void enableOptionsMenu() {
        isFilteringDisabled = false;
        invalidateOptionsMenu();
    }

    Flowable<CardsFilter> procudeConfigFetchCompletable(boolean forced) {
        if (cardsFilter == null || forced) {
            return Flowable.fromCallable(new Callable<CardsFilter>() {
                @Override
                public CardsFilter call() throws Exception {
                    //TODO: Make a propper usecase or incorporate in cache retrieval use cases
                    cardsFilter = HeartstoneApplication.database().produceCardsFilter();
                    expListFilteringOptions.post(new Runnable() {
                        @Override
                        public void run() {
                            filterAdapter.setCardsFilter(cardsFilter);
                            filterAdapter.notifyDataSetChanged();

                            invalidateOptionsMenu();
                        }
                    });

                    return cardsFilter;
                }
            });
        } else {
            return Flowable.fromCallable(new Callable<CardsFilter>() {
                @Override
                public CardsFilter call() throws Exception {
                    return cardsFilter;
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_filter_show_list);
        item.setEnabled(!isFilteringDisabled);

        if (cardsFilter != null) {
            switch (cardsFilter.getAlhabetic()) {
                case ASCENDING:
                    menu.findItem(R.id.action_filter_sorting_ascending).setVisible(true);
                    menu.findItem(R.id.action_filter_sorting_descending).setVisible(false);
                    break;
                case DESCENDING:
                    menu.findItem(R.id.action_filter_sorting_ascending).setVisible(false);
                    menu.findItem(R.id.action_filter_sorting_descending).setVisible(true);
                    break;
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter_show_list:
                if (drawerLayout.isDrawerOpen(Gravity.END)) {
                    drawerLayout.closeDrawer(Gravity.END);
                } else {
                    drawerLayout.openDrawer(Gravity.END);
                }
                break;
            case R.id.action_filter_sorting_ascending:
                cardsFilter.alhabetic(CardsFilter.Sorting.DESCENDING);
                invalidateOptionsMenu();
                loadLocalData();
                break;
            case R.id.action_filter_sorting_descending:
                cardsFilter.alhabetic(CardsFilter.Sorting.ASCENDING);
                invalidateOptionsMenu();
                loadLocalData();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(STATE_RECYCLER_POSITION, rclrLayoutManager.findFirstVisibleItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        savedInstanceState.getInt(STATE_RECYCLER_POSITION, rclrLayoutManager.findFirstVisibleItemPosition());
    }

    /**
     * Subscribes to given flowable.
     * <p>
     * On result will generate new PagedList which will be set to adapter.
     *
     * @param flowable - flowable to subscribe
     * @return flowable disposable
     */
    @NonNull
    private Disposable subscribeForCards(Flowable<LiveData<PagedList<Card>>> flowable) {
        disableOptionsMenu();

        return flowable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<LiveData<PagedList<Card>>>() {
                    @Override
                    public void accept(LiveData<PagedList<Card>> liveData) throws Exception {
                        MainActivity.this.liveData = liveData;
                        liveData.observe(MainActivity.this, MainActivity.this);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        swipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                viewStateSwitcher.error();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }

                });
    }

    @Override
    public void onClick(View view) {
        int childAdapterPosition = rclrCardList.getChildAdapterPosition(view);
        startActivity(DetailActivity.intent(this, childAdapterPosition, cardsFilter));
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        loadLocalData();
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onChanged(@Nullable PagedList<Card> cards) {
        int lastKnownPosition = MainActivity.this.lastKnownPostition;
        cards.loadAround(lastKnownPosition);

        //FIXME: recreating adapter on each call
        //Otherwise will have strange 'position jumping' behaviour
        adapter = new CardListAdapter(MainActivity.this);
        adapter.setList(cards);
        rclrCardList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //FIXME: Respect the changed dataset. Now will try to scroll to card position without respect to card id
        //Restore scrolled position
        //rclrLayoutManager.scrollToPositionWithOffset(0, 0);

        if (adapter.getItemCount() > 0) {
            viewStateSwitcher.content();
        } else {
            viewStateSwitcher.empty();
        }

        swipeRefreshLayout.setRefreshing(false);
        enableOptionsMenu();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}