package nl.splendo.assignment.posytive.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import nl.splendo.assignment.posytive.R;
import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.helpers.LifecycleInteractor;
import nl.splendo.assignment.posytive.mvp.ViewMVP;
import nl.splendo.assignment.posytive.mvp.bindings.SplashBinding;
import nl.splendo.assignment.posytive.presenters.SplashPresenter;

import java.util.List;

import spork.Spork;
import spork.android.BindLayout;
import spork.android.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@BindLayout(R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements LifecycleInteractor, SplashBinding.View {

    /** Used for logging */
    public static final String TAG = "SplashActivity";

    /** Presenter deciding what is going in in this view. In charge of fetching/pre-loading data */
    private SplashPresenter mPresenter;

    @BindView(R.id.progress_layout)
    protected ViewGroup vProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spork.bind(this);
        mPresenter = new SplashPresenter(this, savedInstanceState != null);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onViewActive(this);
    }

    @Override
    public void onPause() {
        mPresenter.onViewInactive();
        super.onPause();
    }

    @Override
    public <F extends Fragment & ViewMVP> void showFragment(Class<F> fragmentClass, @Nullable Bundle bundle, boolean addToBackStack) {
        Log.e(TAG, "Not planning to show fragments on Splash Screen, showFragment method not implemented for lack of layout");
    }

    @Override
    public void changeToolBarTitle(String title) {
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public void showToastMessage(String message) {
        // TODO: implement in base activity
    }

    @Override
    public void setProgressBar(boolean show) {
        if (show) {
            vProgressBar.setVisibility(VISIBLE);
        } else {
            vProgressBar.setVisibility(GONE);
        }
    }

    @Override
    public void setTitle(String title) {
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showCardsLister(List<Card> cards) {
        changeActivity(MainActivity.class, null, true);
    }
}
