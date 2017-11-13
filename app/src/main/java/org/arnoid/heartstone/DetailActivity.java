package org.arnoid.heartstone;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.arnoid.heartstone.data.util.CardsFilter;
import org.arnoid.heartstone.usecase.LoadCachedCardIdsListUseCase;
import org.arnoid.heartstone.view.details.CardDetailsFragmentPagerAdapter;
import org.arnoid.heartstone.view.util.ViewStateSwitcher;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Displays card details.
 */
public class DetailActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String INTENT_KEY_CARD_POSITION = "INTENT_KEY_CARD_POSITION";
    private static final String INTENT_KEY_CARD_FILTER = "INTENT_KEY_CARD_FILTER";

    private ViewPager vpgrCardsPager;
    private ViewStateSwitcher viewStateSwitcher;
    private LiveData<PagedList<String>> cardsLiveData;
    private CardDetailsFragmentPagerAdapter fragmentPagerAdapter;
    private int cardPosition = -1;
    private Disposable cardsDisposable;
    private CardsFilter filter;

    /**
     * Produces intent for specified card.
     *
     * @param ctx
     * @param cardPosition - card position in card query to show
     * @param cardsFilter  - card filter for query, so we can navigate the query
     * @return intent
     */
    public static Intent intent(Context ctx, int cardPosition, CardsFilter cardsFilter) {
        Intent intent = new Intent(ctx, DetailActivity.class);
        intent.putExtra(INTENT_KEY_CARD_POSITION, cardPosition);
        intent.putExtra(INTENT_KEY_CARD_FILTER, cardsFilter.toJson());

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (savedInstanceState != null) {
            cardPosition = savedInstanceState.getInt(INTENT_KEY_CARD_POSITION, -1);
            filter = CardsFilter.fromJson(savedInstanceState.getString(INTENT_KEY_CARD_FILTER));
        } else if (intent != null) {
            cardPosition = intent.getIntExtra(INTENT_KEY_CARD_POSITION, -1);
            filter = CardsFilter.fromJson(intent.getStringExtra(INTENT_KEY_CARD_FILTER));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentPagerAdapter = new CardDetailsFragmentPagerAdapter(getSupportFragmentManager());

        vpgrCardsPager = findViewById(R.id.vpgr_card_pager);
        vpgrCardsPager.addOnPageChangeListener(this);

        viewStateSwitcher = ViewStateSwitcher.Builder.withView(findViewById(android.R.id.content))
                .content(vpgrCardsPager)
                .empty(R.id.v_empty_state)
                .error(R.id.v_error_state)
                .build();

        viewStateSwitcher.content();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (cardsDisposable != null) {
            cardsDisposable.dispose();
        }

        LoadCachedCardIdsListUseCase usecase = new LoadCachedCardIdsListUseCase(HeartstoneApplication.database());
        usecase.setPrefetchDistance(3);
        usecase.setPageSize(3);
        usecase.setFilter(filter);
        usecase.setInitialPosition(cardPosition);
        cardsDisposable = subscribeForCards(usecase.execute());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (cardsDisposable != null) {
            cardsDisposable.dispose();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(INTENT_KEY_CARD_POSITION, cardPosition);
        outState.putString(INTENT_KEY_CARD_FILTER, filter.toJson());
    }

    /**
     * Subscribes for cards flowable.
     *
     * @param flowable
     * @return
     */
    @NonNull
    private Disposable subscribeForCards(Flowable<LiveData<PagedList<String>>> flowable) {
        return flowable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<LiveData<PagedList<String>>>() {
                    @Override
                    public void accept(LiveData<PagedList<String>> liveData) throws Exception {
                        cardsLiveData = liveData;

                        liveData.observe(DetailActivity.this, new Observer<PagedList<String>>() {
                            @Override
                            public void onChanged(@Nullable PagedList<String> cards) {

                                cards.loadAround(cardPosition);
                                fragmentPagerAdapter.setData(cards);
                                fragmentPagerAdapter.notifyDataSetChanged();

                                if (fragmentPagerAdapter.getCount() > 0 && cardPosition >= 0) {

                                    if (vpgrCardsPager.getAdapter() == null) {
                                        vpgrCardsPager.setAdapter(fragmentPagerAdapter);
                                    }
                                    vpgrCardsPager.setCurrentItem(cardPosition);
                                } else {
                                    viewStateSwitcher.empty();
                                }
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        viewStateSwitcher.error();
                    }
                });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //We gonna save the card position to support screen rotation and restore
        this.cardPosition = position;
        PagedList<String> pagedList = cardsLiveData.getValue();
        //preload values around
        if (pagedList != null) {
            pagedList.loadAround(position);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
