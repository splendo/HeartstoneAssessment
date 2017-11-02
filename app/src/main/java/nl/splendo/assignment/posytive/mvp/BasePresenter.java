package nl.splendo.assignment.posytive.mvp;


import android.content.Context;

import nl.splendo.assignment.posytive.helpers.threading.MainUiThread;
import nl.splendo.assignment.posytive.helpers.threading.ThreadExecutor;

/**
 * Abstract base class to be extended by any MVP Presenter.
 * It contains common attributes and methods to be shared across Presenters.
 *
 * @param <V> a generic type to indicate a type of MVP View
 */
public abstract class BasePresenter<V extends ViewMVP> implements PresenterMVP<V> {

    /** Reference to a generic MVP View, every presenter should have one */
    protected Context mContext;

    /** Reference to a generic MVP View, every presenter should have one */
    protected V mView;

    /** Where every presenter should execute their background tasks */
    protected ThreadExecutor mThreadExecutor;

    /** Where every presenter should execute their UI tasks */
    protected MainUiThread mMainUiThread;

    public BasePresenter(V view) {
        mContext = view.getContext();
        mThreadExecutor = ThreadExecutor.getInstance();
        mMainUiThread = MainUiThread.getInstance();
    }

    @Override
    public void onViewActive(V view) { mView = view; }

    @Override
    public void onViewInactive() { mView = null; }

    public V getView() {
        return mView;
    }
}
