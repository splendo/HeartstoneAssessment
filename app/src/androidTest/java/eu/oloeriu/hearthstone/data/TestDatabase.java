package eu.oloeriu.hearthstone.data;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import eu.oloeriu.hearthstone.TestingUtils;
import eu.oloeriu.hearthstone.tools.Card;
import eu.oloeriu.hearthstone.tools.TestUtils;
import eu.oloeriu.hearthstone.tools.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Bogdan Oloeriu on 17/03/2018.
 */
public class TestDatabase {
    private static ContentResolver contentResolver;
    private static Resources resources;

    @BeforeClass
    public static void setUpClass() {
        contentResolver = InstrumentationRegistry.getTargetContext().getContentResolver();
        resources = InstrumentationRegistry.getTargetContext().getResources();
    }

    @Before
    public void setUp() throws Exception {
        deleteAllRecordsFromProvider();
    }

    @After
    public void tareDown() throws Exception{
        deleteAllRecordsFromProvider();
    }

    private void deleteAllRecordsFromProvider() {

        TestingUtils.deleteAllRecordsFromProvider(contentResolver);
    }

    @Test
    public void insertCard() {
        CardSql cardSql = new CardSql();
        cardSql.setCardId("my id");
        cardSql.setName("Bogdan");


        contentResolver.insert(CardTable.CONTENT_URI, CardTable.getContentValues(cardSql, true));

        Cursor cursor = contentResolver.query(CardTable.CONTENT_URI,
                null, //projection
                null, //selection
                null, //selectionArgs
                null); //sortOrder


        CardSql dataCard = CardTable.getRow(cursor, true);
        assertTrue("Not the same name", dataCard.getName().equals(cardSql.getName()));
    }

    @Test
    public void testQueryExamples(){
        Map<String, List<Card>> map = Utils.loadCardsFromJson(resources);
        int totalCards = 0; // should be 3116 cards (quite allot of them)
        for (List<Card> cardsSet : map.values()) {
            totalCards += cardsSet.size();
        }
        System.out.println("Total cards = " + totalCards);

        ContentResolver contentResolver = InstrumentationRegistry.getTargetContext().getContentResolver();
        Map<String, List<Card>> allMap = Utils.loadCardsFromJson(resources);

        Utils.initialPersistCardsInDatabase(contentResolver, resources, allMap);

        //get specific subsets from the database
        String selection = "type =? ";
        String selectionArgs[] = {"Minion"};

        Cursor cursor = contentResolver.query(CardTable.CONTENT_URI, null, selection, selectionArgs, null);

        int databaseCount = cursor.getCount();
        assertTrue("Wee have some records", databaseCount > 0);

        while (cursor.moveToNext()){
            String[] column = cursor.getColumnNames();
            System.out.println(column);
        }
    }

    @Test
    public void utilsUpdateFavorite(){
        Card card1 = TestingUtils.getTyrandeWhisperwind(resources);
        CardSql cardSql1 = CardSql.buildFromCard(card1);
        contentResolver.insert(CardTable.CONTENT_URI, CardTable.getContentValues(cardSql1, true));

        CardSql cardSql2 = CardSql.buildFromCard(card1);
        cardSql2.setCardId("modified2");
        contentResolver.insert(CardTable.CONTENT_URI, CardTable.getContentValues(cardSql2,true));

        Cursor allCursor = contentResolver.query(CardTable.CONTENT_URI,null,null,null,null);
        assertEquals("Expected 2 cards on the database", 2, allCursor.getCount());
        allCursor.close();

        String cardId = card1.getCardId();
        CardSql whisperA = TestingUtils.getCardById(cardId ,contentResolver);
        assertEquals("Card should not be favorite", 0, whisperA.getCardsFavorite());

        Utils.updateFavorite(cardId, 1,contentResolver);
        whisperA = TestingUtils.getCardById(cardId,contentResolver);
        assertEquals("Card should be favorite now", 1, whisperA.getCardsFavorite());
    }
}