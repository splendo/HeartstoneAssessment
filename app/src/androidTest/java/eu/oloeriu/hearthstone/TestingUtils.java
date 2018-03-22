package eu.oloeriu.hearthstone;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.Cursor;

import java.util.List;
import java.util.Map;

import eu.oloeriu.hearthstone.data.CardSql;
import eu.oloeriu.hearthstone.data.CardTable;
import eu.oloeriu.hearthstone.tools.Card;
import eu.oloeriu.hearthstone.tools.Utils;

import static org.junit.Assert.assertEquals;

/**
 * Created by Bogdan Oloeriu on 17/03/2018.
 */

public class TestingUtils {
    /**
     * It deletes all the records from the database
     *
     * @param contentResolver the application content resolver
     */
    public static void deleteAllRecordsFromProvider(ContentResolver contentResolver) {
        contentResolver.delete(
                CardTable.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = contentResolver.query(CardTable.CONTENT_URI, null, null, null, null);
        assertEquals("TestingUtils Not able to clear the CardTable", 0, cursor.getCount());
    }

    /**
     * It just returns a card. I just piked a card that looks really nice
     * and used its name inside the name of the method
     *
     * @return a card;
     */
    public static Card getTyrandeWhisperwind(Resources resources) {
        Map<String, List<Card>> map = Utils.loadCardsFromJson(resources);
        List<Card> cards = map.get("Hero Skins");
        for (Card card: cards){
            if (card.getCardId().equals("HERO_09a")){
                return card;
            }
        }
        throw new IllegalStateException("Not able to locate in tests Tyrande Whisperwind");
    }

    /**
     * It gets a card from the database using the cardId
     * @param cardId
     * @return
     */
    public static CardSql getCardById(String cardId, ContentResolver contentResolver){
        String selection = CardTable.FIELD__ID + " =? ";
        String selectionArgs[] = {cardId};
        Cursor cursor = contentResolver.query(CardTable.CONTENT_URI, null,selection ,selectionArgs,null);
        if (cursor.getCount() != 1){
            throw new IllegalStateException("Card not found. Id = " + cardId);
        }
        cursor.moveToFirst();
        CardSql cardSql = CardTable.getRow(cursor,true);
        return cardSql;
    }
}
