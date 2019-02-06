package laurens.hearthstoneassessment.dataaccess.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import laurens.hearthstoneassessment.model.CardStatus

@Dao
interface CardStatusDao {


    @Query("SELECT * from card_status WHERE cardId=:cardId")
    fun getCardStatusLive(cardId: String): LiveData<CardStatus>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cardStatus: CardStatus): Long

    @Update
    fun update(cardStatus: CardStatus)

    @Transaction
    fun updateOrInsert(cardStatus: CardStatus) {
        if (insert(cardStatus) == -1L) {
            update(cardStatus)
        }
    }

    @Delete
    fun delete(cardStatus: CardStatus)
}