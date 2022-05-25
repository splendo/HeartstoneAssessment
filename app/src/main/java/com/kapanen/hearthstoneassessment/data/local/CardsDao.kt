package com.kapanen.hearthstoneassessment.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kapanen.hearthstoneassessment.model.DbCard

@Dao
interface CardsDao {

    @Query("SELECT * FROM Cards")
    fun observeDbCards(): LiveData<List<DbCard>>

    @Query("SELECT * FROM Cards WHERE cardType = :cardType")
    fun observeDbCardsByType(cardType: String): LiveData<List<DbCard>>

    @Query("SELECT * FROM Cards WHERE cardId = :cardId")
    fun observeDbCardById(cardId: String): LiveData<DbCard>

    @Query("SELECT * FROM Cards")
    suspend fun getDbCards(): List<DbCard>

    @Query("SELECT * FROM Cards WHERE cardType = :cardType")
    suspend fun getDbCardByType(cardType: String): List<DbCard>

    @Query("SELECT * FROM Cards WHERE cardId = :cardId")
    suspend fun getDbCardById(cardId: String): DbCard

    @Query("SELECT * FROM Cards WHERE isFavorite = :isFavorite")
    fun observeFavouriteDbCards(isFavorite: Boolean = true): LiveData<List<DbCard>>

    @Query("SELECT * FROM Cards WHERE isFavorite = :isFavorite")
    fun getFavouriteDbCards(isFavorite: Boolean = true): List<DbCard>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDbCard(card: DbCard)

    @Update
    suspend fun updateDbCard(card: DbCard): Int

    @Query("DELETE FROM Cards")
    suspend fun deleteDbCards()

}
