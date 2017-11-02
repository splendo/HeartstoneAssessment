package nl.splendo.assignment.posytive.data;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import nl.splendo.assignment.posytive.data.cache.DatabaseDataSource;
import nl.splendo.assignment.posytive.data.local.LocalDataSource;
import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.data.remote.CardsRemoteDataSource;
import nl.splendo.assignment.posytive.data.repositories.DataCollectionRepository;
import nl.splendo.assignment.posytive.helpers.NetworkHelper;
import nl.splendo.assignment.posytive.helpers.callbacks.GetCardsCallback;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataRepositoryTest {

    @Mock
    LocalDataSource<Card> mockLocalDataSource;

    @Mock
    DatabaseDataSource<Card> mockDBDataSource;

    @Mock
    CardsRemoteDataSource mockRemoteDataSource;

    @Mock
    Context mockContext;

    @Mock
    GetCardsCallback mockGetCardsCallback;

    @Captor
    ArgumentCaptor<GetCardsCallback> getCardsCallbackCaptor;

    private DataCollectionRepository dataRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        dataRepository = new DataCollectionRepository(mockRemoteDataSource, mockLocalDataSource);
    }

    @After
    public void tearDown() {
        //dataRepository.destroy();
    }

    @Test
    public void getCards_shouldCallRemoteDataSourceAndStoreLocally() {

        dataRepository.getItems(mockContext, mockGetCardsCallback);

        verify(mockRemoteDataSource).getData(getCardsCallbackCaptor.capture());

        List<Card> cards = new ArrayList<>();
        getCardsCallbackCaptor.getValue().onSuccess(cards);

        verify(mockDBDataSource).storeData(cards);
        verify(mockGetCardsCallback).onSuccess(cards);
    }

}
