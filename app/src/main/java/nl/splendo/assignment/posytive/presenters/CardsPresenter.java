package nl.splendo.assignment.posytive.presenters;

import android.content.Context;
import android.util.Log;

import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.mvp.bindings.CardsBinding;
import nl.splendo.assignment.posytive.mvp.bindings.GenericMVPBinding;
import nl.splendo.assignment.posytive.presenters.generics.ItemListPresenter;
import nl.splendo.assignment.posytive.ui.generics.ItemListFragment;
import com.raizlabs.android.dbflow.structure.BaseModel;


/**
 * The Presenter that fetches Card data by calling {@see DataCollectionRepository} at the request of
 * its View "{@link CardsBinding.View}", and then delivers the data back to its View.
 * The presenter also calls other relevant methods of its View such as for
 * showing/hiding progress bar and for showing Toast messages.
 * The Presenter subscribes to its View lifecycle by allowing
 * the View to call the Presenter's {@see #onViewActive(Object)} and {@link #onViewInactive()}
 * to reference/unreference its View. This allows its View to be GCed and prevents memory leaks.
 * The Presenter also checks if its View is active before calling any of its methods.
 */
public class CardsPresenter
       extends ItemListPresenter<Card, CardsBinding.ListerView>
       implements CardsBinding.ListPresenter<CardsBinding.ListerView> {

    /** Used for logging */
    public static final String TAG = "CardsPresenter";

    /**
     * Constructor
     *
     * @param view the mvp view connected to this presenter
     */
    public CardsPresenter(CardsBinding.ListerView view) {
        super(view, Card.class);
        Log.i(TAG, "CardPresenter created");
    }

    /**
     * Beginning of presenter logic
     */
    @Override
    public void init() {
        // only if view is active and repo initialized (initRepo called before init so expected)
        if (!mIsInitialized && getView() != null && mDataRepository != null) {
            Log.i(TAG, "CardsPresenter init(): start at page " + ItemListFragment.STARTING_PAGE_INDEX);
            getCards(getView().getContext());
            mIsInitialized = true;
        }
    }

    @Override
    public void onViewActive(CardsBinding.ListerView view) {
        mView = view;
        if (!mIsInitialized) init();
    }

    @Override
    public void getCards(Context context) {
        if (getView() != null) {
            Log.d(TAG, "getCards()");
            getData(context, ItemListFragment.STARTING_PAGE_INDEX);
        }
    }

    @Override
    public void onCardSelected(Context context, Card card) {
        Log.i(TAG, "Clicked on Card item " + card.getTitle());
        mMainUiThread.post(() -> getView().showCardDetails(card));
    }

    @Override
    public <M extends BaseModel & GenericMVPBinding.ListableModel> void onItemSelected(Context context, M which) {
        Log.d(TAG, "onItemSelected() should be implemented by onCardSelected(): " + which.toString());
        onCardSelected(context, (Card) which);
    }
}