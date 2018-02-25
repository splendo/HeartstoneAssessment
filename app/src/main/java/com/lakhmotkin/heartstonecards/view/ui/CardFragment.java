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

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lakhmotkin.heartstonecards.R;
import com.lakhmotkin.heartstonecards.repository.model.Card;

import timber.log.Timber;

/**
 * A fragment for displaying an image.
 */
public class CardFragment extends Fragment {

    private static final String KEY_CARD = "com.lakhmotkin.heartstonecards.card.key.card";
    private static final String KEY_SELECTED_CARD = "com.lakhmotkin.heartstonecards.card.key.selected";

    private ImageView mCardImage;
    private TextView mCardTitle;
    private TextView mCardSet;
    private TextView mCardText;
    private ImageView mFavoritesButton;
    private ImageView mGoldButton;
    private TextView mCardClass;

    public static CardFragment newInstance(Card card, Boolean selected) {
        CardFragment fragment = new CardFragment();
        Bundle argument = new Bundle();
        argument.putSerializable(KEY_CARD, card);
        argument.putBoolean(KEY_SELECTED_CARD, selected);
        fragment.setArguments(argument);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_card, container, false);
        Bundle arguments = getArguments();
        Card card = (Card) arguments.getSerializable(KEY_CARD);
        Boolean selected = arguments.getBoolean(KEY_SELECTED_CARD);

        mCardTitle = (TextView) view.findViewById(R.id.card_title);
        mCardTitle.setText(card.getName());
        mCardSet = (TextView) view.findViewById(R.id.card_set);
        mCardSet.setText(card.getCardSet());
        mCardClass = (TextView) view.findViewById(R.id.card_class);
        mCardClass.setText(card.getPlayerClass());
        mCardText = (TextView) view.findViewById(R.id.card_text);
        mCardText.setText(card.getText());


        mFavoritesButton = (ImageView) view.findViewById(R.id.favorite_card_button);
        mFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mGoldButton = (ImageView) view.findViewById(R.id.gold_card_button);
        mGoldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage(card.getImgGold(), null);
            }
        });
        int goldButtonVisibility = (card.getImgGold() != null) ? View.VISIBLE : View.INVISIBLE;
        mGoldButton.setVisibility(goldButtonVisibility);
        Timber.d("onCreateView card.getImgGold() = " + card.getImgGold());

        mCardImage = (ImageView) view.findViewById(R.id.card_image);
        mCardImage.setTransitionName(card.getCardId());
        if (selected) {
            loadImage(card.getImg(), mImageReadyListener);
        } else {
            loadImage(card.getImg(), null);
        }
        return view;
    }

    private void loadImage(String url, RequestListener<Drawable> listener) {
        Glide.with(this)
                .load(url)
                .listener(listener)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blank_card)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(mCardImage);
    }

    private RequestListener<Drawable> mImageReadyListener = new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable>
                target, boolean isFirstResource) {
            if (getParentFragment() != null) {
                getParentFragment().startPostponedEnterTransition();
            }
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
                target, DataSource dataSource, boolean isFirstResource) {
            if (getParentFragment() != null) {
                getParentFragment().startPostponedEnterTransition();
            }
            return false;
        }
    };

}
