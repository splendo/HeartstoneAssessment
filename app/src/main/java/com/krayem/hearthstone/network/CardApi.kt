package com.krayem.hearthstone.network

import com.krayem.hearthstone.model.Card
import com.krayem.hearthstone.model.ListApiResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface CardApi {

    @GET("/cards/all")
    fun getAll(): Observable<List<Card>>
}