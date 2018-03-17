package eu.oloeriu.hearthstone.tools;

import android.content.ContentResolver;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import eu.oloeriu.hearthstone.R;
import eu.oloeriu.hearthstone.data.CardSql;
import eu.oloeriu.hearthstone.data.CardTable;

/**
 * Created by Bogdan Oloeriu on 15/03/2018.
 */

public class Utils {
    private static String logTag = Constants.LOG_TAG;


    public static Map<String, List<Card>> loadCardsFromJson(Resources resources) {

        InputStream is = resources.openRawResource(R.raw.cards);
        Reader reader = new InputStreamReader(is);

        Type type = new TypeToken<Map<String, List<Card>>>() {
        }.getType();
        Gson gson = new Gson();
        Map<String, List<Card>> map = gson.fromJson(reader, type);
        return map;
    }

    public static void initialPersistCardsInDatabase(ContentResolver contentResolver, Resources resources, Map<String, List<Card>> cardsMap) {

        for (List<Card> cards : cardsMap.values()) {
            for (Card card : cards) {
                CardSql sqlCard = CardSql.buildFromCard(card);
                contentResolver.insert(CardTable.CONTENT_URI, CardTable.getContentValues(sqlCard, true));
            }
        }
    }


}
