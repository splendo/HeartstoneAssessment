package com.lakhmotkin.heartstonecards.repository.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.lakhmotkin.heartstonecards.repository.model.Card;

import java.util.List;

/**
 * Created by Igor Lakhmotkin on 25.02.2018, for HeartstoneAssessment.
 */

@Dao
public interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Card card);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Card> cards);

    @Query("SELECT * FROM cards ORDER BY name")
    List<Card> loadAll();

    @Query("SELECT * FROM cards WHERE favorite = 1 ORDER BY name")
    List<Card> loadAllFavorites();

    @Query("SELECT * FROM cards WHERE cardId = :cardId")
    List<Card> loadAllByCardId(String cardId);

    @Query("SELECT * FROM cards WHERE mechanics LIKE '%' || :mechanic || '%' AND rarity = :rarity ORDER BY name")
    List<Card> loadAllByMechanic(String mechanic, String rarity);

    @Query("SELECT * FROM cards WHERE text LIKE '%' || :text || '%' ORDER BY name")
    List<Card> loadAllByText(String text);

    @Query("DELETE FROM cards")
    void deleteAllCards();
}
