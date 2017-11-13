package org.arnoid.heartstone.controller.network;

import org.arnoid.heartstone.data.util.CardSets;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Network interface
 */
public interface NetworkInterface {

    @GET("/cards.json")
    Flowable<CardSets> getCardsList();
}
