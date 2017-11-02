package nl.splendo.assignment.posytive.data.remote;

import android.content.Context;
import android.util.Log;

import nl.splendo.assignment.posytive.BuildConfig;
import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.data.models.responses.CardsListResponse;
import nl.splendo.assignment.posytive.data.remote.api.CardApiService;

import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static nl.splendo.assignment.posytive.presenters.generics.ItemListPresenter.PAGINATION_SIZE;

/**
 * The class for fetching card data from Github on a background thread and returning data via
 * callbacks on the main UI thread
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class CardsRemoteDataSource extends RemoteDataSource<Card> {

    /** Used for logging */
    public static final String TAG = "CardsRemoteDataSource";

    /** Network interface required by Retrofit lib */
    private CardApiService mApiService;

    public CardsRemoteDataSource(Context context) {
        super(Card.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mApiService = retrofit.create(CardApiService.class);
    }
    
    @Override
    public Card getData(GetDataCallback<Card> callback) {
        Log.d(TAG, "getRemoteItems(Card)");
        getCards(callback);
        return null;
    }

    @Override
    public Card getData(GetDataCallback<Card> callback, int page) {
        Log.d(TAG, "getRemoteItems(Card): page " + page);
        getCards(callback, page);
        return null;
    }

    @Override
    public Card getDataById(final String id, final GetItemCallback<Card> callback) {
        String failureReason = "[NOT IMPLEMENTED] GitHub data source cannot be filtered by ID (" + id + ")";
        // TODO: implement if appengine backend supports it
        Throwable sorry = new Throwable(failureReason);
        mUiThread.post(() -> callback.onFailure(sorry));
        return null;
    }

    /**
     * Better named, and more specifically typed, version for getData(callback)
     */
    private void getCards(final GetDataCallback callback) {
        String fullUrl = BuildConfig.BASE_API_URL + BuildConfig.CARD_API_ENDPOINT;
        Log.d(TAG, "Fetching items from " + fullUrl);
        retrofit2.Call<Map<String, List<Card>>> call = mApiService.getCards(fullUrl);
        call.enqueue(new retrofit2.Callback<Map<String, List<Card>>>() {

            @Override
            public void onResponse(retrofit2.Call<Map<String, List<Card>>> call,
                                   retrofit2.Response<Map<String, List<Card>>> response) {
                if (response.isSuccessful()) {
                    CardsListResponse itemsResponse = new CardsListResponse(response.body());
                    callback.onSuccess(itemsResponse.getCards());
                } else {
                    callback.onFailure(new Throwable("Network error: " + response.message()));
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Map<String, List<Card>>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    /**
     * Better named, and more specifically typed, version for getData(callback, page)
     */
    private void getCards(final GetDataCallback callback, int page) {
        String fullUrl = BuildConfig.BASE_API_URL + BuildConfig.CARD_API_ENDPOINT;
        String query = generatePaginatedQueryString(page, PAGINATION_SIZE);
        Log.d(TAG, "Fetching cards @ page " + page + " from " + fullUrl + query);
        retrofit2.Call<Map<String, List<Card>>> call = mApiService.getCards(fullUrl + query);
        call.enqueue(new retrofit2.Callback<Map<String, List<Card>>>() {

            @Override
            public void onResponse(retrofit2.Call<Map<String, List<Card>>> call,
                                   retrofit2.Response<Map<String, List<Card>>> response) {
                if (response.isSuccessful()) {
                    CardsListResponse itemsResponse = new CardsListResponse(response.body());
                    callback.onSuccess(itemsResponse.getCards());
                } else {
                    callback.onFailure(new Throwable("Network error: " + response.message()));
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Map<String, List<Card>>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

}
