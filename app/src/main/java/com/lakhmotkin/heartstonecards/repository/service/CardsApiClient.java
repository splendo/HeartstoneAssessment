package com.lakhmotkin.heartstonecards.repository.service;

import com.lakhmotkin.heartstonecards.repository.model.CardSets;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Igor Lakhmotkin on 23.02.2018
 */

public interface CardsApiClient {

    @GET("/maestromaster/HeartstoneAssessment/master/cards.json")
    Observable<CardSets> fetchCardSets();

}

