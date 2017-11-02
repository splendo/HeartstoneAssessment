package nl.splendo.assignment.posytive.presenters;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import nl.splendo.assignment.posytive.R;
import nl.splendo.assignment.posytive.data.cache.DatabaseDataSource;
import nl.splendo.assignment.posytive.data.local.LocalDataSource;
import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.data.remote.CardsRemoteDataSource;
import nl.splendo.assignment.posytive.data.remote.RemoteDataSource;
import nl.splendo.assignment.posytive.data.repositories.DataCollectionRepository;
import nl.splendo.assignment.posytive.helpers.callbacks.GetCardsCallback;
import nl.splendo.assignment.posytive.mvp.BasePresenter;
import nl.splendo.assignment.posytive.mvp.bindings.SplashBinding;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The Presenter that fetches data by calling {@see DataCollectionRepository} at the request of
 * its View "{@link SplashBinding.View}", and then stores it before notifying the View that
 * is ready to switch.
 * The presenter also calls other relevant methods of its View such as for
 * showing/hiding progress bar and for showing Toast messages.
 * The Presenter subscribes to its View lifecycle by allowing
 * the View to call the Presenter's {@see onViewActive(Object)} and {@link #onViewInactive()}
 * to reference/unreference its View. This allows its View to be GCed and prevents memory leaks.
 * The Presenter also checks if its View is active before calling any of its methods.
 */
public class SplashPresenter extends BasePresenter<SplashBinding.View> implements
        SplashBinding.Presenter {

    /** Used for logging */
    public static final String TAG = "SplashPresenter";

    /** Seconds to wait before going to next screen, if data is ready. Couldn't decide between 1 and 3 */
    private static final int SECONDS_MINIMUM_STAY_HERE = 5;

    /** Main interactor with all the possible data sources that fetches cards */
    private DataCollectionRepository<Card> mDataRepository;

    /** Flag to implement timer, if API/DB replies faster than X seconds, wait before navigating away */
    private AtomicBoolean mShouldWaitLonger = new AtomicBoolean(true);

    /** Short-lived caching of retrieved card, in (probable) case are available before X seconds */
    private List<Card> mCardsToParcel;

    /**
     * Final listener that asynchronously gathers the data, or the error, from the model.
     * Contains the logic for what to do next: go to main screen
     */
    private final GetCardsCallback mDataListener = new GetCardsCallback() {
        @Override
        public void onSuccess(final List<Card> cards) {
            mCardsToParcel = cards;
            boolean readyToGo = !mShouldWaitLonger.get();
            if (readyToGo) {
                navigateToMainScreen();
            } else {
                Log.w(TAG, "Timeout not over yet, but expected data retrieval to be faster, so chill!");
            }
        }

        @Override
        public void onFailure(Throwable throwable) {
            if (mView != null) {
                mMainUiThread.post(() -> mView.setProgressBar(false));
                mView.showToastMessage(mView.getContext().getString(R.string.card_data_error_msg));
            }
        }

        @Override
        public void onDataMiss() {
            if (mView != null) {
                mMainUiThread.post(() -> mView.setProgressBar(false));
                mView.showToastMessage(mView.getContext().getString(R.string.card_data_error_msg));
            }
        }
    };

    public SplashPresenter(SplashBinding.View view) {
        this(view, false);
    }

    /**
     * Constructor: needs the view to display things.
     * If the view was recreated, pass true: it will avoid showing the loader for longer than needed
     *
     * @param view the mvp view compatible with Splash functionalities
     * @param isRestored pass true if is not the first time using this presenter, for this run
     */
    public SplashPresenter(SplashBinding.View view, boolean isRestored) {
        super(view);

        // if recreated (rotation or else) don't block navigation with the timeout
        if (isRestored) mShouldWaitLonger.compareAndSet(true, false);


        RemoteDataSource<Card> remoteData = new CardsRemoteDataSource(view.getContext());
        DatabaseDataSource<Card> dbData = new DatabaseDataSource<>(Card.class, view.getContext());
        LocalDataSource<Card> savedData = new LocalDataSource<>(Card.class, view.getContext());

        // first fetch Online, otherwise check DB. Otherwise (offline first run), use local data
        mDataRepository = new DataCollectionRepository<>(remoteData, dbData, savedData);
    }

    @Override
    public void onViewActive(SplashBinding.View view) {
        super.onViewActive(view);
        mView.setTitle(mView.getContext().getString(R.string.splash_screen_title));
        loadCacheCards(view.getContext());
    }

    @Override
    public void loadCacheCards(final Context context) {
        if (mView == null) {
            return;
        }

        mMainUiThread.post(() -> {
            if (getView() != null) getView().setProgressBar(true);

            // implement a timeout in order to let the Splash screen visible for X seconds
            new Handler().postDelayed(() -> {
                boolean readyToGo = mShouldWaitLonger.compareAndSet(true, false);
                Log.i(TAG, "Timeout is over: ready? " + readyToGo);
                if (readyToGo && mCardsToParcel != null) {
                    Log.v(TAG, "Timeout is over: data ready! ");
                    navigateToMainScreen();
                } else if (readyToGo) {
                    Log.w(TAG, "Timeout is over: data not ready! ");
                } else {
                    Log.w(TAG, "Timeout is over: Unexpected flag state, timeout disabled");
                    // remove block from timeout anyway
                    mShouldWaitLonger.compareAndSet(false, false);
                }
            }, SECONDS_MINIMUM_STAY_HERE * 1000);
        });

        mThreadExecutor.execute(() -> {
            mDataRepository.getItems(context, mDataListener);
        });
    }

    /**
     * Runs on UI Thread and forces view to show the main screen
     */
    private void navigateToMainScreen() {
        if (mView != null) {
            Log.i(TAG, "Go to main screen");
            mMainUiThread.post(() -> {
                mView.showCardsLister(mCardsToParcel);
                mView.setProgressBar(false);
            });
        }
    }
}
