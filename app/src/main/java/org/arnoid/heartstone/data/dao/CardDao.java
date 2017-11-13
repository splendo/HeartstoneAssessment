package org.arnoid.heartstone.data.dao;

import android.arch.paging.LivePagedListProvider;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import org.arnoid.heartstone.data.BaseCard;
import org.arnoid.heartstone.data.Card;
import org.arnoid.heartstone.data.CardClass;
import org.arnoid.heartstone.data.CardMechanic;
import org.arnoid.heartstone.data.CardRarity;
import org.arnoid.heartstone.data.CardType;
import org.arnoid.heartstone.data.relations.CardToCardClass;
import org.arnoid.heartstone.data.relations.CardToCardMechanic;
import org.arnoid.heartstone.data.relations.CardToCardRarity;
import org.arnoid.heartstone.data.relations.CardToCardType;

import java.util.List;

import io.reactivex.Flowable;

/**
 * DAO class.
 */
@Dao
public interface CardDao {

    @Transaction
    @Query(BaseCard.Scheme.Queries.ALL)
    LivePagedListProvider<Integer, Card> getAllList(List<String> cardClasses, List<String> cardMechanics, List<String> cardRarities, List<String> cardTypes, List<Integer> favourites);

    @Query(BaseCard.Scheme.Queries.ALL_CARD_ID)
    LivePagedListProvider<Integer, String> getCardIdList(List<String> cardClasses, List<String> cardMechanics, List<String> cardRarities, List<String> cardTypes, List<Integer> favourites);

    @Transaction
    @Query(BaseCard.Scheme.Queries.ALL_DESC)
    LivePagedListProvider<Integer, Card> getAllListDesc(List<String> cardClasses, List<String> cardMechanics, List<String> cardRarities, List<String> cardTypes, List<Integer> favourites);

    @Query(BaseCard.Scheme.Queries.ALL_CARD_ID_DESC)
    LivePagedListProvider<Integer, String> getCardIdListDesc(List<String> cardClasses, List<String> cardMechanics, List<String> cardRarities, List<String> cardTypes, List<Integer> favourites);

    @Query(BaseCard.Scheme.Queries.CARD_BY_ID)
    Flowable<Card> getCard(String cardId);

    @Query(CardMechanic.Scheme.Queries.ALL)
    List<CardMechanic> getMechanics();

    @Query(CardMechanic.Scheme.Queries.ALL_FILER_BY_NAME)
    List<CardMechanic> getMechanics(List<String> names);

    @Query(CardClass.Scheme.Queries.ALL)
    List<CardClass> getCardClasses();

    @Query(CardClass.Scheme.Queries.ALL_FILER_BY_NAME)
    List<CardClass> getCardClasses(List<String> names);

    @Query(CardRarity.Scheme.Queries.ALL)
    List<CardRarity> getCardRarities();

    @Query(CardRarity.Scheme.Queries.ALL_FILER_BY_NAME)
    List<CardRarity> getCardRarities(List<String> names);

    @Query(CardType.Scheme.Queries.ALL)
    List<CardType> getCardTypes();

    @Query(CardType.Scheme.Queries.ALL_FILER_BY_NAME)
    List<CardType> getCardTypes(List<String> names);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCard(BaseCard card);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertMechanics(CardMechanic mechanics);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertCardClass(CardClass cardClasses);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertCardRarity(CardRarity cardRarity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertCardType(CardType cardTypes);

    @Update
    void updateCard(BaseCard card);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CardToCardMechanic cardToCardMechanic);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CardToCardRarity cardToCardRarity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CardToCardType cardToCardType);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CardToCardClass cardToCardClass);
}
