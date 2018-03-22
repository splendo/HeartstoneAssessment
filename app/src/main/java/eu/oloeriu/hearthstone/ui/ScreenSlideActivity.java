package eu.oloeriu.hearthstone.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import eu.oloeriu.hearthstone.R;
import eu.oloeriu.hearthstone.data.CardSql;
import eu.oloeriu.hearthstone.data.CardTable;
import eu.oloeriu.hearthstone.tools.ZoomOutPageTransformer;

public class ScreenSlideActivity extends AppCompatActivity implements ScreenSlidePageFragment.OnFragmentInteractionListener {

    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Cursor mCursor;

    private String mSortOrder;
    private String mSelection;
    private String mSelectionArgs[];
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        Intent intent = getIntent();
        mSortOrder = intent.getStringExtra(MainActivity.ARG_SORT_ORDER);
        mSelection = intent.getStringExtra(MainActivity.ARG_SELECTION);
        mSelectionArgs = intent.getStringArrayExtra(MainActivity.ARG_SELECTION_ARGS);
        mPosition = intent.getIntExtra(MainActivity.ARG_CURSOR_POSITION, 0);

        mCursor = createCursor();

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setCurrentItem(mPosition);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private Cursor createCursor() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CardTable.CONTENT_URI, null, mSelection, mSelectionArgs, mSortOrder);
        return cursor;
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            mCursor.moveToPosition(position);

            CardSql cardSql = CardTable.getRow(mCursor, false);
            String cardId = cardSql.getCardId();
            String cardName = cardSql.getName();
            String cardSet = cardSql.getCardSet();
            String cardMechanics = cardSql.getMechanics();
            String cardClasses = cardSql.getClasses();
            String cardImageUrl = cardSql.getImg();
            String cardGoldUrl = cardSql.getImgGold();
            int cardFavorite = cardSql.getCardsFavorite();

            ScreenSlidePageFragment screenSlidePageFragment =
                    ScreenSlidePageFragment.newInstance(cardId,
                            cardName,
                            cardSet,
                            cardMechanics,
                            cardClasses,
                            cardImageUrl,
                            cardGoldUrl,
                            cardFavorite);

            return screenSlidePageFragment;
        }

        @Override
        public int getCount() {
            return mCursor.getCount();
        }
    }


}
