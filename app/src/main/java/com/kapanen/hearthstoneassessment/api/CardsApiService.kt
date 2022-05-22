package com.kapanen.hearthstoneassessment.api

import com.kapanen.hearthstoneassessment.model.Cards
import retrofit2.Response
import retrofit2.http.GET

interface CardsApiService {

    @GET("cards.json")
    suspend fun cards(): Response<Cards>

}
