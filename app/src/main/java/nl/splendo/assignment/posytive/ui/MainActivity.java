package nl.splendo.assignment.posytive.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import nl.splendo.assignment.posytive.R;
import nl.splendo.assignment.posytive.helpers.LifecycleInteractor;
import nl.splendo.assignment.posytive.ui.cards.CardGridFragment;

import spork.Spork;
import spork.android.BindLayout;
import spork.android.BindView;

/**
 * The container responsible for showing and destroying relevant {@link Fragment}, handling
 * back and up navigation using the Fragment back stack and maintaining global state.
 */
@BindLayout(R.layout.activity_main)
public class MainActivity extends BaseActivity implements LifecycleInteractor {

    public static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    private Toolbar vToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spork.bind(this);
        setSupportActionBar(vToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        showFragment(CardGridFragment.class, null, false);
    }

    @Override
    protected @IdRes
    int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public void changeToolBarTitle(String title) {
        vToolbar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed() called, bs = " + getSupportFragmentManager().getBackStackEntryCount());
        super.onBackPressed();
    }
}
