package nl.splendo.assignment.posytive.mvp;

/**
 * Common and basic needs a Presenter has, in the MVP pattern.
 * Also defines a basic interface to use for binding the components.
 */
public interface PresenterMVP<V extends ViewMVP> {

    /**
     * Used to get notified by view about play time
     *
     * @param view the MVP view type related to this  particular presenter
     */
    void onViewActive(V view);

    /**
     * Used to get notified by view about sleep time
     */
    void onViewInactive();
}
