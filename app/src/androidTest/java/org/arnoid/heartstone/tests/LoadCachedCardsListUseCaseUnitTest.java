package org.arnoid.heartstone.tests;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.arnoid.heartstone.MainActivity;
import org.arnoid.heartstone.controller.DatabaseController;
import org.arnoid.heartstone.data.BaseCard;
import org.arnoid.heartstone.data.Card;
import org.arnoid.heartstone.data.CardClass;
import org.arnoid.heartstone.data.CardMechanic;
import org.arnoid.heartstone.data.CardRarity;
import org.arnoid.heartstone.data.CardType;
import org.arnoid.heartstone.data.HeartstoneDatabase;
import org.arnoid.heartstone.data.util.CardsFilter;
import org.arnoid.heartstone.usecase.LoadCachedCardsListUseCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LoadCachedCardsListUseCaseUnitTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule(MainActivity.class);

    public static final String CARD_ID = "cardId";
    public static final String CARD_SET = "Set";
    public static final String CARD_CLASS = "Class";
    public static final String CARD_TYPE = "Type";
    public static final String CARD_RARITY = "Rarity";
    public static final String CARD_MECHANIC = "Mechanic";
    private Card mockCard;
    private DatabaseController databaseController;

    @Before
    public void before() throws InterruptedException {
        //TODO: move this to super class

        mockCard = new Card();
        mockCard.setBaseCard(produceMockBaseCard());

        Set<CardRarity> raritySet = new HashSet<>();
        raritySet.add(produceMockRarirty());
        mockCard.setRarity(raritySet);

        Set<CardMechanic> mechanicSet = new HashSet<>();
        mechanicSet.add(produceMockMechanic());
        mockCard.setMechanics(mechanicSet);

        Set<CardType> cardTypeSet = new HashSet<>();
        cardTypeSet.add(produceMockType());
        mockCard.setType(cardTypeSet);

        Set<CardClass> cardClassSet = new HashSet<>();
        cardClassSet.add(produceMockClass());
        mockCard.setClasses(cardClassSet);

        Context appContext = InstrumentationRegistry.getTargetContext();

        databaseController = new DatabaseController(appContext) {

            @Override
            public boolean isFirstInsert() {
                return true;
            }

            @Override
            public void setFirstInsert(boolean value) {

            }

            @NonNull
            @Override
            protected HeartstoneDatabase produceDatabase(Context ctx) {
                return Room.inMemoryDatabaseBuilder(ctx.getApplicationContext(), HeartstoneDatabase.class)
                        .build();
            }
        };

    }

    @NonNull
    private BaseCard produceMockBaseCard() {
        BaseCard baseCard = new BaseCard();
        baseCard.setCardSet(CARD_SET);
        baseCard.setCardId(CARD_ID);
        return baseCard;
    }

    private CardClass produceMockClass() {
        return new CardClass(CARD_CLASS);
    }

    private CardType produceMockType() {
        return new CardType(CARD_TYPE);
    }

    @NonNull
    private CardRarity produceMockRarirty() {
        return new CardRarity(CARD_RARITY);
    }

    @NonNull
    private CardMechanic produceMockMechanic() {
        return new CardMechanic(CARD_MECHANIC);
    }

    @After
    public void after() {
        databaseController.close();
    }

    /**
     * Normal flow test
     * ASSERT: inserted data equals to returned data.
     * @throws InterruptedException
     */
    @Test
    public void NormalFlowTest() throws InterruptedException {
        //Inject initial data
        final Semaphore semaphore = new Semaphore(1);
        semaphore.acquire();
        Flowable.just(mockCard)
                .map(databaseController.insert())
                .subscribe(new Consumer<Card>() {
                    @Override
                    public void accept(Card card) throws Exception {
                        semaphore.release();
                    }
                });

        semaphore.tryAcquire(5, TimeUnit.SECONDS);

        CardsFilter cardsFilter = databaseController.produceCardsFilter();

        LoadCachedCardsListUseCase useCase = new LoadCachedCardsListUseCase(databaseController);
        useCase.setFilter(cardsFilter);
        useCase.setInitialPosition(0);

        useCase.execute()
                .subscribe(new Consumer<LiveData<PagedList<Card>>>() {
                    @Override
                    public void accept(final LiveData<PagedList<Card>> pagedListLiveData) throws Exception {
                        pagedListLiveData.observe(activityRule.getActivity(), new Observer<PagedList<Card>>() {
                            @Override
                            public void onChanged(@Nullable PagedList<Card> cards) {
                                pagedListLiveData.removeObserver(this);
                                cards.detach();

                                assertEquals(1, cards.size());
                                assertEquals(mockCard.getBaseCard(), cards.get(0).getBaseCard());

                                semaphore.release();
                            }
                        });
                    }
                });

        semaphore.tryAcquire(5, TimeUnit.SECONDS);
    }

    /**
     * Test with empty results list
     * ASSERT: response is mpty
     * @throws InterruptedException
     */
    @Test
    public void EmptyListFlowTest() throws InterruptedException {
        //Inject initial data
        final Semaphore semaphore = new Semaphore(1);
        semaphore.acquire();

        CardsFilter cardsFilter = databaseController.produceCardsFilter();

        LoadCachedCardsListUseCase useCase = new LoadCachedCardsListUseCase(databaseController);
        useCase.setFilter(cardsFilter);
        useCase.setInitialPosition(0);

        useCase.execute()
                .subscribe(new Consumer<LiveData<PagedList<Card>>>() {
                    @Override
                    public void accept(final LiveData<PagedList<Card>> pagedListLiveData) throws Exception {
                        pagedListLiveData.observe(activityRule.getActivity(), new Observer<PagedList<Card>>() {
                            @Override
                            public void onChanged(@Nullable PagedList<Card> cards) {
                                pagedListLiveData.removeObserver(this);
                                cards.detach();

                                assertEquals(0, cards.size());

                                semaphore.release();
                            }
                        });
                    }
                });

        semaphore.tryAcquire(5, TimeUnit.SECONDS);
    }
}
