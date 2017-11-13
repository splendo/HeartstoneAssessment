package org.arnoid.heartstone.tests;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.arnoid.heartstone.controller.DatabaseController;
import org.arnoid.heartstone.controller.NetworkController;
import org.arnoid.heartstone.data.BaseCard;
import org.arnoid.heartstone.data.Card;
import org.arnoid.heartstone.data.CardClass;
import org.arnoid.heartstone.data.CardMechanic;
import org.arnoid.heartstone.data.CardRarity;
import org.arnoid.heartstone.data.CardType;
import org.arnoid.heartstone.data.HeartstoneDatabase;
import org.arnoid.heartstone.data.util.CardSets;
import org.arnoid.heartstone.usecase.PullRemoteCardsListAndSaveUseCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class PullRemoteCardsListAndSaveUseCaseUnitTest {

    public static final String CARD_ID = "cardId";
    public static final String CARD_SET = "Set";
    public static final String CARD_CLASS = "Class";
    public static final String CARD_TYPE = "Type";
    public static final String CARD_RARITY = "Rarity";
    public static final String CARD_MECHANIC = "Mechanic";
    private Card mockCard;
    private DatabaseController databaseController;
    private NetworkController networkController;

    @Before
    public void before() {
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

        networkController = new NetworkController() {
            public Flowable<CardSets> getCardsList() {
                CardSets cardSets = new CardSets();

                List<Card> cards = new ArrayList<>();
                cards.add(mockCard);
                cardSets.put(mockCard.getBaseCard().getCardSet(), cards);

                return Flowable.just(cardSets);
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
     * Test normal flow.
     * ASSERT: Entity received from Network controller is equal to one read from database
     *
     * @throws InterruptedException
     */
    @Test
    public void NormalFlowTest() throws InterruptedException {
        PullRemoteCardsListAndSaveUseCase useCase = new PullRemoteCardsListAndSaveUseCase(networkController, databaseController);

        final Semaphore semaphore = new Semaphore(1);

        semaphore.acquire();
        useCase.execute()
                .andThen(
                        databaseController.getCard(CARD_ID)
                ).subscribe(new Consumer<Card>() {
            @Override
            public void accept(Card card) throws Exception {
                //Query returns card with BaseCard
                assertEquals(mockCard.getBaseCard(), card.getBaseCard());
                semaphore.release();
            }
        });
        semaphore.tryAcquire(5, TimeUnit.SECONDS);
    }

    /**
     * Test flow with network exception.
     * ASSERT: on results returned and error handler is called
     *
     * @throws InterruptedException
     */
    @Test
    public void FlowWithNetworkExceptionTest() throws InterruptedException {
        PullRemoteCardsListAndSaveUseCase useCase = new PullRemoteCardsListAndSaveUseCase(new NetworkController() {
            @Override
            public Flowable<CardSets> getCardsList() {
                return Flowable.error(new IOException("dummy"));
            }
        }, databaseController);

        final Semaphore semaphore = new Semaphore(1);

        semaphore.acquire();

        useCase.execute()
                .andThen(databaseController.getCard(CARD_ID))
                .subscribe(new Consumer<Card>() {
                               @Override
                               public void accept(Card card) throws Exception {
                                   fail();
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                semaphore.release();
                            }
                        });
        semaphore.tryAcquire(5, TimeUnit.SECONDS);
    }

}
