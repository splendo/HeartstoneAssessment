package com.lakhmotkin.heartstonecards.viewmodel;

import com.lakhmotkin.heartstonecards.repository.data.CardsRepository;
import com.lakhmotkin.heartstonecards.repository.data.CardsRepositoryType;
import com.lakhmotkin.heartstonecards.repository.data.db.DbHelper;
import com.lakhmotkin.heartstonecards.repository.model.Card;
import com.lakhmotkin.heartstonecards.repository.service.CardsApiClient;
import com.lakhmotkin.heartstonecards.repository.service.CardsClient;
import com.lakhmotkin.heartstonecards.repository.service.CardsClientType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Igor Lakhmotkin on 25.02.2018, for HeartstoneAssessment.
 */
public class CardsListViewModelTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private CardsRepositoryType mRepository;

    @Mock
    private DbHelper dbHelper;

    @Mock
    private CardsApiClient mProductsApiClient;

    private CardsListViewModel mViewModel;

    private final List<Card> MANY_CARDS = Arrays.asList(new Card("CS2_041e"), new Card("HERO_09"), new Card("EX1_399e"));

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mViewModel = new CardsListViewModel(mRepository);
    }

    @Test
    public void shouldPassProducts() {
        when(mRepository.fetchRemoteCards()).thenReturn(Observable.just(MANY_CARDS));

        TestObserver<List<Card>> subscriber = mRepository
                .fetchRemoteCards()
                .test();

        subscriber.awaitTerminalEvent();
        subscriber.assertComplete();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        assertEquals(subscriber.values().get(0).get(0).getName(), MANY_CARDS.get(0).getName());
    }

    @Test
    public void shouldShowError() {
        when(mProductsApiClient.fetchCardSets()).thenReturn(Observable.error(new SocketTimeoutException()));
        CardsClientType testProductsClient = new CardsClient(mProductsApiClient);

        CardsRepositoryType testRepository = new CardsRepository(testProductsClient, dbHelper);

        TestObserver<List<Card>> subscriber = testRepository
                .fetchRemoteCards()
                .test();

        subscriber.assertNotComplete();
    }
}