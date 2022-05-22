package com.kapanen.hearthstoneassessment.data.remote

import androidx.lifecycle.LiveData
import com.kapanen.hearthstoneassessment.model.Card

interface RemoteCardsDataSource {

    fun observeCards(): LiveData<Result<List<Card>>>

    suspend fun getCards(): Result<List<Card>>

    suspend fun refresh()

}
