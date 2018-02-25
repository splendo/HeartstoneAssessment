/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lakhmotkin.heartstonecards.view.ui;

import android.animation.Animator;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.lakhmotkin.heartstonecards.R;
import com.lakhmotkin.heartstonecards.repository.model.Card;
import com.lakhmotkin.heartstonecards.view.adapter.CardsPagerAdapter;
import com.lakhmotkin.heartstonecards.viewmodel.CardsListViewModel;
import com.lakhmotkin.heartstonecards.viewmodel.CardsListViewModelFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

/**
 * A fragment for displaying a pager of images.
 */
public class CardsPagerFragment extends Fragment {
    private static final String KEY_CARDS = "com.lakhmotkin.heartstonecards.pager.key.card";

    @Inject
    CardsListViewModelFactory cardsModelFactory;

    private ViewPager mCardsPager;
    private List<Card> mCards = new ArrayList<>();
    private CardsPagerAdapter mPagerAdapter;
    private ViewGroup mCardsRoot;
    private ImageView mBgImage;

    public static CardsPagerFragment newInstance(List<Card> cards) {
        CardsPagerFragment fragment = new CardsPagerFragment();
        Bundle argument = new Bundle();
        argument.putSerializable(KEY_CARDS, (Serializable) cards);
        fragment.setArguments(argument);
        return fragment;
    }

    @Nullable
    @Override
    @SuppressWarnings({"unchecked"})
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView");
        mCards = (List<Card>) getArguments().getSerializable(KEY_CARDS);
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        mCardsPager = (ViewPager) view.findViewById(R.id.cards_pager);
        mCardsRoot = (ViewGroup) view.findViewById(R.id.cards_pager_root);
        mBgImage = (ImageView) view.findViewById(R.id.cards_pager_bg);
        Glide.with(this)
                .load(R.drawable.cards_pager_bg)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                        mBgImage.setImageDrawable(resource);
                    }
                });
        mBgImage.post(new Runnable() {
            @Override
            public void run() {
                circularReveal(mBgImage);
            }
        });

        mPagerAdapter = new CardsPagerAdapter(this, mCards);
        mCardsPager.setAdapter(mPagerAdapter);
        mCardsPager.setCurrentItem(CardsGridActivity.currentPosition);
        mCardsPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                CardsGridActivity.currentPosition = position;
            }
        });

        prepareSharedElementTransition();
        if (savedInstanceState == null) {
            postponeEnterTransition();
        }

        return view;
    }

    protected void circularReveal(View view) {
        float finalRadius = (float) (Math.max(view.getWidth(), view.getHeight()) * 1.1);

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(view, view.getWidth()/2, view.getHeight()/2, 0, finalRadius);
        circularReveal.setDuration(500);
        circularReveal.setInterpolator(new AccelerateInterpolator());

        view.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onActivityCreated(savedInstanceState);

        CardsListViewModel viewModel = ViewModelProviders.of(this, cardsModelFactory)
                .get(CardsListViewModel.class);
        viewModel.error().observe(this, this::onError);
        viewModel.progress().observe(this, this::onProgress);
        viewModel.prepare();

        Timber.d("onActivityCreated");
    }

    private void onProgress(Boolean aBoolean) {

    }

    private void onError(Throwable throwable) {

    }

    private void prepareSharedElementTransition() {
        Transition transition =
                TransitionInflater.from(getContext())
                        .inflateTransition(R.transition.image_shared_element_transition);
        setSharedElementEnterTransition(transition);

        setEnterSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        Fragment currentFragment = (Fragment) mCardsPager.getAdapter()
                                .instantiateItem(mCardsPager, CardsGridActivity.currentPosition);
                        View view = currentFragment.getView();
                        if (view == null) {
                            return;
                        }

                        sharedElements.put(names.get(0), view.findViewById(R.id.card_image));
                    }
                });
    }
}
