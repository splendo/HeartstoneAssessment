package com.krayem.hearthstone.network

import com.krayem.hearthstone.model.Card
import com.krayem.hearthstone.model.ListApiResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CardApi {

    @GET("/cards/all")
    fun getAll(): Observable<MutableList<Card>>

    @POST("/cards")
    fun getCardSet(@Body cardSet:String): Observable<MutableList<Card>>
}