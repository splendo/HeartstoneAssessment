package org.arnoid.heartstone.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.arnoid.heartstone.BuildConfig;
import org.arnoid.heartstone.controller.network.NetworkInterface;
import org.arnoid.heartstone.data.Card;
import org.arnoid.heartstone.data.CardClass;
import org.arnoid.heartstone.data.CardRarity;
import org.arnoid.heartstone.data.CardType;
import org.arnoid.heartstone.data.util.CardClassDeserializer;
import org.arnoid.heartstone.data.util.CardDeserializer;
import org.arnoid.heartstone.data.util.CardRarityDeserializer;
import org.arnoid.heartstone.data.util.CardSets;
import org.arnoid.heartstone.data.util.CardTypeDeserializer;

import java.util.Set;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Network controller to handle all the network calls
 */
public class NetworkController {

    private final NetworkInterface networkInterface;

    public NetworkController() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CardClass.class, new CardClassDeserializer())
                .registerTypeAdapter(new TypeToken<Set<CardRarity>>(){}.getType(), new CardRarityDeserializer())
                .registerTypeAdapter(new TypeToken<Set<CardType>>(){}.getType(), new CardTypeDeserializer())
                .registerTypeAdapter(Card.class, new CardDeserializer())
                .create();

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.BACKEND_URL)
                .build();

        networkInterface = retrofit.create(NetworkInterface.class);
    }

    public Flowable<CardSets> getCardsList() {
        return networkInterface.getCardsList();
    }
}
