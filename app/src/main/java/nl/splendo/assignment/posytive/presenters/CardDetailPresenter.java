package nl.splendo.assignment.posytive.presenters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import nl.splendo.assignment.posytive.data.DataSource;
import nl.splendo.assignment.posytive.data.cache.DatabaseDataSource;
import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.data.remote.CardsRemoteDataSource;
import nl.splendo.assignment.posytive.data.remote.RemoteDataSource;
import nl.splendo.assignment.posytive.data.repositories.DataItemRepository;
import nl.splendo.assignment.posytive.mvp.bindings.CardsBinding;
import nl.splendo.assignment.posytive.mvp.bindings.GenericMVPBinding;
import nl.splendo.assignment.posytive.presenters.generics.BaseItemPresenter;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.apache.commons.lang3.StringUtils;


/**
 * The Presenter that fetches Card data by calling {@see DataCollectionRepository} at the request of
 * its View "{@see CategoriesBinding.View}", and then delivers the data back to its View.
 * The presenter also calls other relevant methods of its View such as for
 * showing/hiding progress bar and for showing Toast messages.
 * The Presenter subscribes to its View lifecycle by allowing
 * the View to call the Presenter's {@see #onViewActive(Object)} and {@link #onViewInactive()}
 * to reference/unreference its View. This allows its View to be GCed and prevents memory leaks.
 * The Presenter also checks if its View is active before calling any of its methods.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class CardDetailPresenter
       extends BaseItemPresenter<Card, CardsBinding.View>
       implements CardsBinding.Presenter<CardsBinding.View> {

    /** Used for logging */
    public static final String TAG = "CardDetailPresenter";

    /**
     * Simple callback for asynchronously receiving a single item of type Card
     * It extends the generic interface that has the same purpose, but automatically casts the results
     * in the success case
     */
    public interface GetCardCallback extends DataSource.GetItemCallback<Card> {

        void onSuccess(Card card);
    }

    /** Flag used to check if the presenter is ready, allows to run init() code only once */
    private boolean mIsInitialized = false;

    /** Reference to the card to display. Could be initialized later, if not provided in constructor */
    private Card mCardToDisplay;

    /**
     * Reference to the id of the card to display, so it can be first fetched from Repository.
     * Used if the instance was not provided in constructor
     */
    private String mCardIdToFetch;

    /**
     * Constructor to use if the instance of the card to display is available
     *
     * @param view the mvp view connected to this presenter
     * @param card the specific card that should be displayed, not nullable
     */
    public CardDetailPresenter(CardsBinding.View view, @NonNull Card card) {
        super(view, Card.class);
        Log.i(TAG, "CardDetailPresenter created");
        mCardToDisplay = card;
        mCardIdToFetch = mCardToDisplay.getId();
    }

    /**
     * Constructor to use if the instance of the card to display is not available (but its id is):
     * fetches the card via a Local/Cached Source by id, then initialize
     *
     * @param view the mvp view connected to this presenter
     */
    public CardDetailPresenter(CardsBinding.View view, String cardId) {
        super(view, Card.class);
        Log.w(TAG, "CardDetailPresenter created, but card yet to be fetched");
        if (!TextUtils.isEmpty(cardId)) {
            mCardIdToFetch = cardId;
        }
    }

    /**
     * Allows to initialize the DataRepository<>
     */
    protected void initRepo() {
        setDataRepository(mContext, Card.class);
    }

    /**
     * Beginning of presenter logic
     */
    public void init() {

        // only if view is active and repo initialized (initRepo called before init so expected)
        if (!mIsInitialized) {
            mIsInitialized = true;
            if (mCardToDisplay != null) {
                initCardUi(mCardToDisplay);
            } else if (StringUtils.isNotEmpty(mCardIdToFetch) && mDataRepository != null) {
                mMainUiThread.post(() -> {
                    if (getView() != null) getView().setProgressBar(true);
                });
                mThreadExecutor.execute(() -> getCard(mContext, mCardIdToFetch));
            } else {
                return;
            }
            Log.i(TAG, "CardDetailPresenter init()");
        }
    }

    /**
     * Receives the card and inits/updates the UI with the important fields to display.
     * Runs on UI Thread
     *
     * @param card the card loaded from DataRepository, or cached
     */
    private void initCardUi(Card card) {
        mCardToDisplay = card;
        mCardIdToFetch = mCardToDisplay.getId();
            mMainUiThread.post(() -> {
                if (getView() != null) {
                    getView().setTitle(card.getTitle());
                    getView().setCardCover(card.getImg());

                    //getView().setFirstMechanic();
                    getView().setFaction(card.getFaction());
                    getView().setPlayerClass(card.getPlayerClass());
                    getView().setRace(card.getRace());
                    getView().setRarity(card.getRarity());
                    getView().setCardSet(card.getCardSet());

                    getView().setCollectable(card.isCollectible());
                    getView().setFavorite(card.isFavorite());

                    getView().setArtist(card.getArtist());
                    getView().setText(card.getText());
                    getView().setFlavor(card.getFlavor());
                    if (card.getCost() >= 0) getView().setCost(card.getCost());
                    getView().setProgressBar(false);
                }
            });
    }

    @Override
    public void onViewActive(CardsBinding.View view) {
        super.onViewActive(view);
        Log.i(TAG, "CardDetailView is active (initialized? " + mIsInitialized + ")");
        if (!mIsInitialized) {
            init();
        } else if (mCardToDisplay != null) {
            // view was restored
            initCardUi(mCardToDisplay);
        } else {
            getCard(mContext, mCardIdToFetch);
        }
    }

    @Override
    public void getCard(Context context, String cardId) {
        if (getView() != null) {
            Log.d(TAG, "getCard(" + cardId + ")");
            mDataRepository.getDataById(context, cardId, mDataListener);
        }
    }

    @Override
    public String getCardId() {
        return mCardIdToFetch;
    }

    @Override
    public void onFavoriteToggled() {
        if(mCardToDisplay != null) {
            mCardToDisplay.setFavorite(!mCardToDisplay.isFavorite());
            mCardToDisplay.save();
            if (getView() != null) {
                getView().setFavorite(mCardToDisplay.isFavorite());
            }
        }
    }

    @Override
    public <M extends BaseModel & GenericMVPBinding.Model> void setDataRepository(Context context, Class<M> dataType) {
        if (Card.class == dataType) {
            DatabaseDataSource<Card> dbData = new DatabaseDataSource<>(Card.class, context);
            RemoteDataSource<Card> apiData = new CardsRemoteDataSource(context);
            mDataRepository = new DataItemRepository(dbData, apiData);
        }
    }

    /**
     * Final listener that asynchronously gathers the data, or the error, from the model.
     * Contains the logic for what to do next.
     */
    protected final GetCardCallback mDataListener = new GetCardCallback() {

        @Override
        public void onSuccess(Card card) {
            Log.d(TAG, "onSuccess(" + card + ")");
            if (mView != null) {
                mMainUiThread.post(() -> {
                    initCardUi(card);
                    mView.setProgressBar(false);
                });
            }
        }

        @Override
        public void onFailure(Throwable throwable) {
            String sorry = "onFailure(" + throwable.getMessage() + ");";
            Log.d(TAG, sorry);
            if (mView != null) {
                mMainUiThread.post(() -> mView.setProgressBar(false));
                mView.showToastMessage(sorry);
            }
        }

        @Override
        public void onDataMiss() {
            Log.d(TAG, "onDataMiss()");
            if (mView != null) {
                mMainUiThread.post(() -> mView.setProgressBar(false));
                mView.showToastMessage("Data missed");
            }
        }
    };

}
