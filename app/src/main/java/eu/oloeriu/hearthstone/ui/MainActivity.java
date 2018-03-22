package eu.oloeriu.hearthstone.ui;

import android.content.Intent;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.reflect.Field;

import eu.oloeriu.hearthstone.R;
import eu.oloeriu.hearthstone.data.CardTable;
import eu.oloeriu.hearthstone.tools.Constants;

public class MainActivity extends AppCompatActivity implements InteractionListener {


    public static final String ARG_SORT_ORDER = "param_sort_order";
    public static final String ARG_SELECTION = "param_selection";
    public static final String ARG_SELECTION_ARGS = "param_selection_arguments";
    public static final String ARG_CURSOR_POSITION = "param_cursor_position";
    private ListFragment mListFragment;
    private GridFragment mGridFragment;
    private String mSortOrder;
    private String mSelection;
    private String mSelectionArgs[];
    private UpdateFilters mCurrentFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                    mCurrentFragment.changeFilters(null, null);
                    return true;
                case R.id.change_filter_type:
                    //mTextMessage.setText(R.string.title_dashboard);
                    FilterByDialog filterByTypeDialog = FilterByDialog.newInstance(FilterByDialog.FILTER.TYPE);
                    filterByTypeDialog.show(getSupportFragmentManager(), "filterByDialog");
                    return true;
                case R.id.navigation_filter_mechanics:
                    FilterByDialog mechanicsDialog = FilterByDialog.newInstance(FilterByDialog.FILTER.MECHANICS);
                    mechanicsDialog.show(getSupportFragmentManager(), "filterByDialog");
                    return true;
                case R.id.navigation_filter_classes:
                    FilterByDialog classesDialog = FilterByDialog.newInstance(FilterByDialog.FILTER.CLASSES);
                    classesDialog.show(getSupportFragmentManager(), "filterByDialog");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sort by card name ascending order
        mSortOrder = CardTable.FIELD_NAME + " ASC";

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        onNavigateListView();
    }


    @Override
    public void onShowDetails(String cardId, int cursorPosition) {
        Log.d(Constants.LOG_TAG, "Time to show details for " + cardId + " - " + cursorPosition);
        Intent intent = new Intent(this, ScreenSlideActivity.class);
        intent.putExtra(ARG_SORT_ORDER, mSortOrder);
        intent.putExtra(ARG_SELECTION, mSelection);
        intent.putExtra(ARG_SELECTION_ARGS, mSelectionArgs);
        intent.putExtra(ARG_CURSOR_POSITION, cursorPosition);
        startActivity(intent);
    }

    @Override
    public void onNavigateGridView() {
        if (mGridFragment == null) {
            mGridFragment = GridFragment.newInstance(mSortOrder, mSelection, mSelectionArgs);
            mCurrentFragment = mGridFragment;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mGridFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        //mGridFragment.changeFilters(mSelection,mSelectionArgs);
    }

    @Override
    public void onNavigateListView() {

        if (mListFragment == null) {
            mListFragment = ListFragment.newInstance(mSortOrder, mSelection, mSelectionArgs);
            mCurrentFragment = mListFragment;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mListFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        //mListFragment.changeFilters(mSelection,mSelectionArgs);
    }

    @Override
    public void onUpdateFilter(String selection, String[] selectionArgs) {
        mSelection = selection;
        mSelectionArgs = selectionArgs;
        //mListFragment = null;
        //onNavigateListView();
        mCurrentFragment.changeFilters(selection, selectionArgs);
    }


}
