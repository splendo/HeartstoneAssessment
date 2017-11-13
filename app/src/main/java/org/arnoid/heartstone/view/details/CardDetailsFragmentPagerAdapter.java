package org.arnoid.heartstone.view.details;

import android.arch.paging.PagedList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.arnoid.heartstone.fragment.FragmentCardDetail;

/**
 * Shows list detailed cards.
 */
public class CardDetailsFragmentPagerAdapter extends FragmentPagerAdapter {

    PagedList<String> data;

    public CardDetailsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        String cardId = data.get(position);
        return FragmentCardDetail.instance(cardId);
    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    public void setData(PagedList<String> data) {
        this.data = data;
    }
}
