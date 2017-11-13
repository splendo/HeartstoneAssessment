package org.arnoid.heartstone.controller;

import android.arch.paging.LivePagedListProvider;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import org.arnoid.heartstone.data.Card;
import org.arnoid.heartstone.data.CardClass;
import org.arnoid.heartstone.data.CardMechanic;
import org.arnoid.heartstone.data.CardRarity;
import org.arnoid.heartstone.data.CardType;
import org.arnoid.heartstone.data.HeartstoneDatabase;
import org.arnoid.heartstone.data.dao.CardDao;
import org.arnoid.heartstone.data.relations.CardToCardClass;
import org.arnoid.heartstone.data.relations.CardToCardMechanic;
import org.arnoid.heartstone.data.relations.CardToCardRarity;
import org.arnoid.heartstone.data.relations.CardToCardType;
import org.arnoid.heartstone.data.util.CardsFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;

/**
 * Controller to handle all the cached data manipulations.
 */
public class DatabaseController {

    private static final String TAG = DatabaseController.class.getSimpleName();

    private static final String DB_NAME = "database";
    private static final String PREF_IS_FIRST_INSERT = "PREF_IS_FIRST_INSERT";

    private static final String SORTING_ASCENDING = "ASC";
    private static final String SORTING_DESCENDING = "DESC";

    private final HeartstoneDatabase db;
    private final SharedPreferences prefs;

    public DatabaseController(Context ctx) {
        db = produceDatabase(ctx);

        prefs = ctx.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
    }

    @NonNull
    protected HeartstoneDatabase produceDatabase(Context ctx) {
        return Room.databaseBuilder(ctx.getApplicationContext(), HeartstoneDatabase.class, DB_NAME)
                .build();
    }

    public void close() {
        db.close();
    }

    public boolean isFirstInsert() {
        return prefs.getBoolean(PREF_IS_FIRST_INSERT, true);
    }

    public void setFirstInsert(boolean firstInsert) {
        prefs.edit()
                .putBoolean(PREF_IS_FIRST_INSERT, firstInsert)
                .apply();
    }

    /**
     * Insert the initial "none" card class and "none" card mechanics.
     * This is used to show/filter cards with no class and no mechanics/
     *
     * @return completable
     */
    public Completable intialInsert() {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.cardDao().insertMechanics(new CardMechanic(CardMechanic.NONE));
                db.cardDao().insertCardClass(new CardClass(CardClass.NONE));
            }
        });
    }

    /**
     * Instantiates card filter with all options from the database.
     *
     * @return cardsFilter
     */
    public CardsFilter produceCardsFilter() {

        CardDao cardDao = db.cardDao();

        CardsFilter cardsFilter = new CardsFilter().mechanics(cardDao.getMechanics())
                .rarity(cardDao.getCardRarities())
                .classes(cardDao.getCardClasses())
                .types(cardDao.getCardTypes())
                .alhabetic(CardsFilter.Sorting.ASCENDING);

        //All the options are checked to show all the data at start
        for (CardsFilter.Filterable mechanic : cardsFilter.getMechanics()) {
            mechanic.setChecked(true);
        }

        for (CardClass cardClass : cardsFilter.getClasses()) {
            cardClass.setChecked(true);
        }

        for (CardType type : cardsFilter.getTypes()) {
            type.setChecked(true);
        }

        for (CardRarity rarity : cardsFilter.getRarity()) {
            rarity.setChecked(true);
        }

        return cardsFilter;
    }

    /**
     * Inserts card into database.
     *
     * @return function
     */
    public Function<Card, Card> insert() {
        return new Function<Card, Card>() {

            @Override
            public Card apply(Card card) throws Exception {
                CardDao cardDao = db.cardDao();

                cardDao.insertCard(card.getBaseCard());

                insertMechanics(card, cardDao);
                insertCardClasses(card, cardDao);
                insertCardRarity(card, cardDao);
                insertCardTypes(card, cardDao);

                Log.d(TAG, "Card with id [" + card.getBaseCard().getCardId() + "] saved");

                db.getInvalidationTracker().refreshVersionsAsync();

                return card;
            }
        };
    }

    /**
     * Inserts card type into databse.
     *
     * @param card
     * @param cardDao
     */
    private void insertCardTypes(Card card, CardDao cardDao) {
        if (card.getType() != null) {

            List<String> namesFilter = new ArrayList<>();
            for (CardType cardType : card.getType()) {
                namesFilter.add(cardType.getName());
            }

            List<CardType> cachedTypees = cardDao.getCardTypes();

            for (CardType cardType : card.getType()) {
                Log.d(TAG, "inserting type [" + cardType + "] for card [" + card.getBaseCard().getCardId() + "]");
                CardToCardType cardToCardType = new CardToCardType();

                for (CardType cachedType : cachedTypees) {
                    if (cachedType.getName().equalsIgnoreCase(cardType.getName())) {
                        cardToCardType.setCardTypeId(cachedType.getId());
                        break;
                    }
                }

                if (cardToCardType.getCardTypeId() == CardToCardType.UNDEFINED) {
                    long cardTypeId = cardDao.insertCardType(cardType);
                    cardToCardType.setCardTypeId(cardTypeId);
                }

                cardToCardType.setCardId(card.getBaseCard().getCardId());

                cardDao.insert(cardToCardType);
            }
        }
    }

    /**
     * Inserts card rarity
     *
     * @param card
     * @param cardDao
     */
    private void insertCardRarity(Card card, CardDao cardDao) {
        if (card.getRarity() != null) {
            List<String> namesFilter = new ArrayList<>();
            for (CardRarity cardRarity : card.getRarity()) {
                namesFilter.add(cardRarity.getName());
            }

            List<CardRarity> cachedRarityes = cardDao.getCardRarities();

            for (CardRarity cardRarity : card.getRarity()) {
                Log.d(TAG, "inserting rarity [" + cardRarity + "] for card [" + card.getBaseCard().getCardId() + "]");
                CardToCardRarity cardToCardRarity = new CardToCardRarity();

                for (CardRarity cachedRarity : cachedRarityes) {
                    if (cachedRarity.getName().equalsIgnoreCase(cardRarity.getName())) {
                        cardToCardRarity.setCardRarityId(cachedRarity.getId());
                        break;
                    }
                }

                if (cardToCardRarity.getCardRarityId() == CardToCardRarity.UNDEFINED) {
                    long cardRarityId = cardDao.insertCardRarity(cardRarity);
                    cardToCardRarity.setCardRarityId(cardRarityId);
                }

                cardToCardRarity.setCardId(card.getBaseCard().getCardId());

                cardDao.insert(cardToCardRarity);
            }
        }
    }

    /**
     * Inserts card class.
     * <p>
     * If card has no class, then card will be assigned to "none" class
     *
     * @param card
     * @param cardDao
     */
    private void insertCardClasses(Card card, CardDao cardDao) {
        if (card.getClasses() != null) {
            List<String> namesFilter = new ArrayList<>();
            for (CardClass cardClass : card.getClasses()) {
                namesFilter.add(cardClass.getName());
            }

            List<CardClass> cachedClasses = cardDao.getCardClasses();

            for (CardClass cardClass : card.getClasses()) {
                linkCardAndCardClass(card, cardDao, cachedClasses, cardClass);
            }
        } else {
            List<String> namesFilter = new ArrayList<>();
            namesFilter.add(CardClass.NONE);

            List<CardClass> cachedClasses = cardDao.getCardClasses(namesFilter);
            linkCardAndCardClass(card, cardDao, cachedClasses, cachedClasses.get(0));
        }
    }

    private void linkCardAndCardClass(Card card, CardDao cardDao, List<CardClass> cachedClasses, CardClass cardClass) {
        Log.d(TAG, "inserting class [" + cardClass + "] for card [" + card.getBaseCard().getCardId() + "]");
        CardToCardClass cardToCardClass = new CardToCardClass();

        for (CardClass cachedClass : cachedClasses) {
            if (cachedClass.getName().equalsIgnoreCase(cardClass.getName())) {
                cardToCardClass.setCardClassId(cachedClass.getId());
                break;
            }
        }

        if (cardToCardClass.getCardClassId() == CardToCardClass.UNDEFINED) {
            long cardClassId = cardDao.insertCardClass(cardClass);
            cardToCardClass.setCardClassId(cardClassId);
        }

        cardToCardClass.setCardId(card.getBaseCard().getCardId());

        cardDao.insert(cardToCardClass);
    }

    /**
     * Inserts card mechanics into db.
     * <p>
     * If card has no mechanics then "none" mechanics wiill be assigned.
     *
     * @param card
     * @param cardDao
     */
    private void insertMechanics(Card card, CardDao cardDao) {
        if (card.getMechanics() != null) {

            List<String> namesFilter = new ArrayList<>();
            for (CardMechanic mechanic : card.getMechanics()) {
                namesFilter.add(mechanic.getName());
            }

            List<CardMechanic> cachedMechanics = cardDao.getMechanics(namesFilter);

            for (CardMechanic mechanic : card.getMechanics()) {
                linkCardAndCardMechanic(card, cardDao, cachedMechanics, mechanic);
            }
        } else {
            List<String> namesFilter = new ArrayList<>();
            namesFilter.add(CardMechanic.NONE);

            List<CardMechanic> cachedMechanics = cardDao.getMechanics(namesFilter);
            linkCardAndCardMechanic(card, cardDao, cachedMechanics, cachedMechanics.get(0));
        }
    }

    private void linkCardAndCardMechanic(Card card, CardDao cardDao, List<CardMechanic> cachedMechanics, CardMechanic mechanic) {
        Log.d(TAG, "inserting mechanic [" + mechanic + "] for card [" + card.getBaseCard().getCardId() + "]");
        CardToCardMechanic cardToCardMechanic = new CardToCardMechanic();

        for (CardMechanic cachedMechanic : cachedMechanics) {
            if (cachedMechanic.getName().equalsIgnoreCase(mechanic.getName())) {
                cardToCardMechanic.setCardMechanicId(cachedMechanic.getId());
                break;
            }
        }

        if (cardToCardMechanic.getCardMechanicId() == CardToCardMechanic.UNDEFINED) {
            long mechanicId = cardDao.insertMechanics(mechanic);
            cardToCardMechanic.setCardMechanicId(mechanicId);
        }

        cardToCardMechanic.setCardId(card.getBaseCard().getCardId());

        cardDao.insert(cardToCardMechanic);
    }

    /**
     * Updates card record in db.
     *
     * @param card
     * @return
     */
    public Completable update(final Card card) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.cardDao().updateCard(card.getBaseCard());
            }
        });
    }

    /**
     * Returns flowable with cards according to cards filter
     *
     * @param filter
     * @return
     */
    public Flowable<LivePagedListProvider<Integer, Card>> getCardsList(final CardsFilter filter) {
        return Flowable.fromCallable(new Callable<LivePagedListProvider<Integer, Card>>() {
            @Override
            public LivePagedListProvider<Integer, Card> call() throws Exception {
                List<String> cardClasses = getCardClassesFilterOptions(filter);
                List<String> cardMechanics = getCardMechanicsFilterOptions(filter);
                List<String> cardRarities = getCardRaritiesFilterOptions(filter);
                List<String> cardTypes = getCardTypesFilterOptions(filter);
                ArrayList<Integer> favourites = getFavouriteFilterOptions(filter);

                LivePagedListProvider<Integer, Card> cardList;
                switch (filter.getAlhabetic()) {
                    case DESCENDING:
                        cardList = db.cardDao().getAllListDesc(cardClasses, cardMechanics, cardRarities, cardTypes, favourites);
                        break;
                    case ASCENDING:
                    default:
                        cardList = db.cardDao().getAllList(cardClasses, cardMechanics, cardRarities, cardTypes, favourites);
                        break;
                }
                return cardList;
            }
        });
    }

    /**
     * Converts card classes to String list.
     *
     * @param filter
     * @return
     */
    @NonNull
    private List<String> getCardClassesFilterOptions(CardsFilter filter) {
        List<String> cardClasses = new ArrayList<>(filter.getClasses().size());
        for (CardClass cardClass : filter.getClasses()) {
            cardClasses.add(cardClass.getName());
        }
        return cardClasses;
    }

    /**
     * Converts card mechanics to String list.
     *
     * @param filter
     * @return
     */
    @NonNull
    private List<String> getCardMechanicsFilterOptions(CardsFilter filter) {
        List<String> cardMechanics = new ArrayList<>(filter.getMechanics().size());
        for (CardMechanic cardMechanic : filter.getMechanics()) {
            if (cardMechanic.isChecked()) {
                cardMechanics.add(cardMechanic.getName());
            }
        }
        return cardMechanics;
    }

    /**
     * Converts card rarity to String list.
     *
     * @param filter
     * @return
     */
    @NonNull
    private List<String> getCardRaritiesFilterOptions(CardsFilter filter) {
        List<String> cardRarities = new ArrayList<>(filter.getRarity().size());
        for (CardRarity cardRarity : filter.getRarity()) {
            if (cardRarity.isChecked()) {
                cardRarities.add(cardRarity.getName());
            }
        }
        return cardRarities;
    }

    /**
     * Converts card type to String list.
     *
     * @param filter
     * @return
     */
    @NonNull
    private List<String> getCardTypesFilterOptions(CardsFilter filter) {
        List<String> cardTypes = new ArrayList<>(filter.getTypes().size());
        for (CardType type : filter.getTypes()) {
            if (type.isChecked()) {
                cardTypes.add(type.getName());
            }
        }
        return cardTypes;
    }

    /**
     * Converts card favouritiness to Integer list.
     *
     * @param filter
     * @return
     */
    @NonNull
    private ArrayList<Integer> getFavouriteFilterOptions(CardsFilter filter) {
        ArrayList<Integer> favourites = new ArrayList<>();
        favourites.add(1);
        if (!filter.isFavouriteOnly()) {
            favourites.add(0);
        }
        return favourites;
    }

    /**
     * Provides flowable with card ids according to the card filter.
     *
     * @param cardsFilter
     * @return
     */
    public Flowable<LivePagedListProvider<Integer, String>> getCardIdsList(final CardsFilter cardsFilter) {
        return Flowable
                .fromCallable(new Callable<LivePagedListProvider<Integer, String>>() {
                    @Override
                    public LivePagedListProvider<Integer, String> call() throws Exception {
                        List<String> cardClasses = getCardClassesFilterOptions(cardsFilter);
                        List<String> cardMechanics = getCardMechanicsFilterOptions(cardsFilter);
                        List<String> cardRarities = getCardRaritiesFilterOptions(cardsFilter);
                        List<String> cardTypes = getCardTypesFilterOptions(cardsFilter);
                        ArrayList<Integer> favourites = getFavouriteFilterOptions(cardsFilter);

                        LivePagedListProvider<Integer, String> cardIdList;
                        switch (cardsFilter.getAlhabetic()) {
                            case DESCENDING:
                                cardIdList = db.cardDao().getCardIdListDesc(cardClasses, cardMechanics, cardRarities, cardTypes, favourites);
                                break;
                            case ASCENDING:
                            default:
                                cardIdList = db.cardDao().getCardIdList(cardClasses, cardMechanics, cardRarities, cardTypes, favourites);
                                break;
                        }

                        return cardIdList;
                    }
                });
    }

    public Flowable<Card> getCard(final String cardId) {
        return db.cardDao().getCard(cardId);
    }

}
