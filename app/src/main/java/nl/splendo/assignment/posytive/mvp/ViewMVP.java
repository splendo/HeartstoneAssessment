package nl.splendo.assignment.posytive.mvp;

import android.content.Context;

/**
 * Common and basic needs a View has, in the MVP pattern.
 * Also defines a basic interface to use for binding the components.
 */
public interface ViewMVP {

    /**
     * Notifies the user about errors or UI events with a short popup text
     *
     * @param message the message the user should see
     */
    void showToastMessage(String message);

    /**
     * Dependently shows or hides the progress bar, to notify the user of data loading
     *
     * @param show true to freeze UI in a waiting state, false to release it
     */
    void setProgressBar(boolean show);

    /**
     * Allows to display the main label of a particular Model, regardless of which.
     *
     * @param title the title to display in this View
     */
    void setTitle(String title);

    /**
     * Provides Context for the View
     * (but leaves freedom of implementation: Activity, Fragment, Android View(s))
     *
     * @return the context
     */
    Context getContext();
}
