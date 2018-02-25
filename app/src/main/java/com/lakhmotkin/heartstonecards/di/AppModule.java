package com.lakhmotkin.heartstonecards.di;


import com.lakhmotkin.heartstonecards.App;
import com.lakhmotkin.heartstonecards.C;
import com.lakhmotkin.heartstonecards.repository.data.CardsRepository;
import com.lakhmotkin.heartstonecards.repository.data.CardsRepositoryType;
import com.lakhmotkin.heartstonecards.repository.service.CardsApiClient;
import com.lakhmotkin.heartstonecards.repository.service.CardsClientType;
import com.lakhmotkin.heartstonecards.repository.service.CardsClient;
import com.lakhmotkin.heartstonecards.viewmodel.CardsListViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by igorlakhmotkin on 23/02/2018.
 */

@Module
public class AppModule {
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    App provideApp() {
        return app;
    }

    @Singleton
    @Provides
    CardsApiClient provideCardsApiClient(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(C.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(CardsApiClient.class);
    }

    @Singleton
    @Provides
    CardsClientType provideCardsClient(CardsApiClient service) {
        return new CardsClient(service);
    }

    @Singleton
    @Provides
    CardsRepositoryType provideCardsRepository(CardsClientType cardsClient) {
        return new CardsRepository(cardsClient);
    }

    @Singleton
    @Provides
    CardsListViewModelFactory provideCardsListViewModelFactory(CardsRepositoryType repository) {
        return new CardsListViewModelFactory(repository);
    }
}
