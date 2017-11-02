package nl.splendo.assignment.posytive.ui.cards;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import nl.splendo.assignment.posytive.data.models.Card;

public class CardDetailViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Card> mCardsToBundle = new ArrayList<>();

    public CardDetailViewPagerAdapter(FragmentManager fragmentManager, List<Card> cards) {
        super(fragmentManager);
        mCardsToBundle.addAll(cards);
    }

    /**
     * Returns total number of pages
     */
    @Override
    public int getCount() {
        return mCardsToBundle == null ? 0 : mCardsToBundle.size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        Card cardToDisplay = getCardAt(position);
        return CardFragment.newInstance(cardToDisplay.getId(), cardToDisplay);
    }

    private Card getCardAt(int position) {
        boolean invalidPosition = position < 0 || position >= getCount();
        return  invalidPosition ? null : mCardsToBundle.get(position);
    }

    public int getPositionOf(Card item) {
        return mCardsToBundle == null ? 0 : mCardsToBundle.indexOf(item);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Card " + position;
    }

}