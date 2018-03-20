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
import eu.oloeriu.hearthstone.tools.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Bogdan Oloeriu on 17/03/2018.
 */
public class TestDatabase {
    private static ContentResolver contentResolver;

    @BeforeClass
    public static void setUpClass() {
        contentResolver = InstrumentationRegistry.getTargetContext().getContentResolver();
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
        Resources resources = InstrumentationRegistry.getTargetContext().getResources();
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
}