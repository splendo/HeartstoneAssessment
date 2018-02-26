package com.lakhmotkin.heartstonecards.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lakhmotkin.heartstonecards.R;
import com.lakhmotkin.heartstonecards.repository.model.Card;
import com.lakhmotkin.heartstonecards.view.adapter.GridAdapter;
import com.lakhmotkin.heartstonecards.viewmodel.CardsListViewModel;
import com.lakhmotkin.heartstonecards.viewmodel.CardsListViewModelFactory;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A fragment for displaying a grid of images.
 */
public class CardsGridFragment extends Fragment {

    @Inject
    CardsListViewModelFactory cardsModelFactory;

    private RecyclerView mCardsRecyclerView;
    private GridAdapter mGridAdapter;
    private ImageView mCardsGridBg;
    private ViewGroup mLoadingContainer;
    private ViewGroup mErrorContainer;
    private TextView mErrorText;

    private CardsListViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        setHasOptionsMenu(true);
        mCardsRecyclerView = (RecyclerView) view.findViewById(R.id.cards_recycler_grid);
        mGridAdapter = new GridAdapter(this);
        mCardsRecyclerView.setAdapter(mGridAdapter);

        mCardsGridBg = view.findViewById(R.id.cards_bg);
        Glide.with(this)
                .load(R.drawable.grid_bg)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                        mCardsGridBg.setImageDrawable(resource);
                    }
                });
        prepareTransitions();
        postponeEnterTransition();

        mLoadingContainer = view.findViewById(R.id.cards_loading);
        mErrorContainer = view.findViewById(R.id.cards_error);
        mErrorText = view.findViewById(R.id.cards_error_text);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this, cardsModelFactory)
                .get(CardsListViewModel.class);
        mViewModel.error().observe(this, this::onError);
        mViewModel.cards().observe(this, this::onCards);
        mViewModel.progress().observe(this, this::onProgress);
        prepare();
    }

    private void prepare() {
        mViewModel.prepare();
    }

    private void fetchFavoriteCards() {
        mErrorContainer.setVisibility(View.INVISIBLE);
        mViewModel.fetchFavorites();
    }

    private void fetchAllCards() {
        mErrorContainer.setVisibility(View.INVISIBLE);
        mViewModel.fetchAllCards();
    }

    private void fetchByMechanic(String mechanic, String rarity) {
        mErrorContainer.setVisibility(View.INVISIBLE);
        mViewModel.fetchAllCardsByMechanic(mechanic, rarity);
    }

    private void fetchByText(String text) {
        mErrorContainer.setVisibility(View.INVISIBLE);
        mViewModel.fetchAllCardsByText(text);
    }

    private void onProgress(Boolean aBoolean) {
        if (aBoolean) {
            mLoadingContainer.setVisibility(View.VISIBLE);
        } else {
            mLoadingContainer.setVisibility(View.INVISIBLE);
        }
    }

    private void onError(Throwable throwable) {
        mErrorText.setText(getString(R.string.error_message_with_message, throwable.getMessage()));
        mErrorContainer.setVisibility(View.VISIBLE);
    }


    private void onCards(List<Card> cards){
        mGridAdapter.setCardList(cards);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scrollToPosition();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_all:
                fetchAllCards();
                break;
            case R.id.menu_item_favorites:
                fetchFavoriteCards();
                break;
            case R.id.menu_item_deathrattle:
                fetchByMechanic("Deathrattle", "Legendary");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void scrollToPosition() {
        mCardsRecyclerView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left,
                                       int top,
                                       int right,
                                       int bottom,
                                       int oldLeft,
                                       int oldTop,
                                       int oldRight,
                                       int oldBottom) {
                mCardsRecyclerView.removeOnLayoutChangeListener(this);
                final RecyclerView.LayoutManager layoutManager = mCardsRecyclerView.getLayoutManager();
                View viewAtPosition = layoutManager.findViewByPosition(CardsGridActivity.currentPosition);
                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)) {
                    mCardsRecyclerView.post(() -> layoutManager.scrollToPosition(CardsGridActivity.currentPosition));
                }
            }
        });
    }

    private void prepareTransitions() {
        setExitTransition(TransitionInflater.from(getContext())
                .inflateTransition(R.transition.grid_exit_transition));

        setExitSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        RecyclerView.ViewHolder selectedViewHolder = mCardsRecyclerView
                                .findViewHolderForAdapterPosition(CardsGridActivity.currentPosition);
                        if (selectedViewHolder == null || selectedViewHolder.itemView == null) {
                            return;
                        }

                        sharedElements
                                .put(names.get(0), selectedViewHolder.itemView.findViewById(R.id.card_image));
                    }
                });
    }
}
