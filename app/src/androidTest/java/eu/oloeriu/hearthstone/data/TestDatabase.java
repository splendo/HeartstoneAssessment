package eu.oloeriu.hearthstone.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.oloeriu.hearthstone.TestingUtils;

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
//        contentResolver.delete(
//                CardTable.CONTENT_URI,
//                null,
//                null
//        );
//
//        Cursor cursor = contentResolver.query(CardTable.CONTENT_URI, null, null, null, null);
//        assertEquals("Not able to clear the CardTable", 0, cursor.getCount());
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
}