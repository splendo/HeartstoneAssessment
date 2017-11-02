package nl.splendo.assignment.posytive.helpers;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import nl.splendo.assignment.posytive.mvp.ViewMVP;

/**
 * Base interface to be implemented by the hosting {@link AppCompatActivity} of each fragment that
 * need to perform navigation action (in general, actions that activities should be involved for)
 * Forces implementers to provide only fragments that are considered MVP Views
 */
public interface LifecycleInteractor {

    /**
     * Used to show Fragment.
     *
     * @param fragmentClass a {@link ViewMVP} class
     * @param bundle an (optional) {@link Bundle}, to pass data to the new Fragment
     * @param addToBackStack a boolean to add transaction to fragment back stack
     * @param <F> a generic type to indicate type/subclass of {@link Fragment} AND {@link ViewMVP}
     */
    <F extends Fragment & ViewMVP> void showFragment(Class<F> fragmentClass, @Nullable Bundle bundle,
                                                     boolean addToBackStack);

    /**
     * Used to open a new Activity
     *
     * @param activityClass the class of a new Activity to open
     * @param bundle an (optional) {@link Bundle}, to pass data to the new Activity
     * @param <A> a generic type of Activity
     */
    <A extends AppCompatActivity> void changeActivity(Class<A> activityClass, @Nullable Bundle bundle);

    /**
     * If the implementing activity has a Toolbar, this method can be used to change its title
     *
     * @param title the new title to display in Toolbar
     */
    void changeToolBarTitle(String title);
}
