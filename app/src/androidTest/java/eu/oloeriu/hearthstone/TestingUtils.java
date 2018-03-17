package eu.oloeriu.hearthstone;

import android.content.ContentResolver;
import android.database.Cursor;

import eu.oloeriu.hearthstone.data.CardTable;

import static org.junit.Assert.assertEquals;

/**
 * Created by Bogdan Oloeriu on 17/03/2018.
 */

public class TestingUtils {
    public static void deleteAllRecordsFromProvider(ContentResolver contentResolver) {
        contentResolver.delete(
                CardTable.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = contentResolver.query(CardTable.CONTENT_URI, null, null, null, null);
        assertEquals("TestingUtils Not able to clear the CardTable", 0, cursor.getCount());
    }
}
