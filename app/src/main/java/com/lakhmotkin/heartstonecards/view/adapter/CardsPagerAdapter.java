package com.lakhmotkin.heartstonecards.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lakhmotkin.heartstonecards.repository.model.Card;
import com.lakhmotkin.heartstonecards.view.ui.CardFragment;
import com.lakhmotkin.heartstonecards.view.ui.CardsGridActivity;

import java.util.List;

public class CardsPagerAdapter extends FragmentStatePagerAdapter {

    private List<Card> mCardsList;

    public CardsPagerAdapter(Fragment fragment, List<Card> cards) {
        super(fragment.getChildFragmentManager());
        mCardsList = cards;
    }

    public void setCardsList(List<Card> list){
        mCardsList = list;
    }

    @Override
    public int getCount() {
        return mCardsList.size();
    }

    @Override
    public Fragment getItem(int position) {
        boolean selected = (position == CardsGridActivity.currentPosition);
        return CardFragment.newInstance(mCardsList.get(position), selected);
    }
}
