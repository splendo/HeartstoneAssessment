package nl.splendo.assignment.posytive.mvp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import nl.splendo.assignment.posytive.helpers.LifecycleInteractor;

/**
 * Abstract base class to be extended by any MVP View such as {@link Fragment} and
 * {@see ActivityCompat}.
 * It contains common attributes and methods to be shared across Presenters.
 */
public abstract class BaseView extends Fragment implements ViewMVP {

    /** Reference to the Activity loading this fragment, for navigation/interaction purposes */
    protected LifecycleInteractor mActivityInteractionHelper;

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public abstract void setProgressBar(boolean show);

    @Override
    public abstract void setTitle(String title);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivityInteractionHelper = (LifecycleInteractor) getActivity();
    }

    @Override
    public void onDetach() {
        mActivityInteractionHelper = null;
        super.onDetach();
    }
}
