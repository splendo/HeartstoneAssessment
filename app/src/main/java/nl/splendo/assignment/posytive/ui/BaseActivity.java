package nl.splendo.assignment.posytive.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import nl.splendo.assignment.posytive.R;
import nl.splendo.assignment.posytive.mvp.ViewMVP;
import nl.splendo.assignment.posytive.mvp.bindings.GenericMVPBinding;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * The base container responsible for helping with the lifecycle of {@link Fragment} and
 * handling navigation using the Fragment back stack.
 */
public abstract class BaseActivity extends AppCompatActivity implements
        FragmentManager.OnBackStackChangedListener {

    /** Flag indicating if there was a back press impacting the fragment back stack */
    protected boolean hasPopped = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public <A extends AppCompatActivity> void changeActivity(Class<A> activityClass, @Nullable Bundle bundle) {
        changeActivity(activityClass, bundle, true);
    }

    public <A extends AppCompatActivity> void changeActivity(Class<A> activityClass, @Nullable Bundle bundle,
                                                             boolean addToBackStack) {
        Intent activityChangingIntent = new Intent(this, activityClass);

        if (!addToBackStack) activityChangingIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        if (bundle != null) activityChangingIntent.putExtras(bundle);
        startActivity(activityChangingIntent);
    }

    public <F extends Fragment & ViewMVP> void showFragment(Class<F> fragmentClass, Bundle bundle,
                                                            boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(
                fragmentClass.getSimpleName());
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
                fragment.setArguments(bundle);
            } catch (InstantiationException e) {
                throw new RuntimeException("New Fragment should have been created", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("New Fragment should have been created", e);
            }
        }

        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
                R.anim.slide_out_left, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.replace(getFragmentContainerId(), fragment, fragmentClass.getSimpleName());


        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragmentClass.getSimpleName());
        }

        fragmentTransaction.commit();
    }

    public <F extends Fragment & ViewMVP> void showFragment(Class<F> fragmentClass) {
        showFragment(fragmentClass, null, false);
    }

    /**
     * Removes the last fragment and let caller know if there were more in back stack
     * @return
     */
    public boolean popFragmentBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        int backCount = fm.getBackStackEntryCount();
        if (backCount > 0) {
            getSupportFragmentManager().popBackStack();
            return true;
        }
        return false;
    }

    private void shouldShowActionBarUpButton() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            hasPopped = popFragmentBackStack();
            if (hasPopped) return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        hasPopped = popFragmentBackStack();
        if (!hasPopped) super.onBackPressed();
    }

    @Override
    public void onBackStackChanged() {
        shouldShowActionBarUpButton();
        if (hasPopped) {
            Fragment backFragment = getSupportFragmentManager().findFragmentById(getFragmentContainerId());
            if (backFragment != null) {
                ((GenericMVPBinding.View) backFragment).reload();
                Log.d("popFragmentBackStack", "called reload for " + backFragment.getTag());
            }
            hasPopped = false;
        }
    }

    protected abstract @IdRes
    int getFragmentContainerId();


}


