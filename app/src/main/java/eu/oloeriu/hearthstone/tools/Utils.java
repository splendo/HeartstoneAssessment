package eu.oloeriu.hearthstone.tools;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
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

        InputStream is = resources.openRawResource(R.raw.clean_cards);
        Reader reader = new InputStreamReader(is);

        Type type = new TypeToken<Map<String, List<Card>>>() {
        }.getType();
        Gson gson = new Gson();
        Map<String, List<Card>> map = gson.fromJson(reader, type);
        return map;
    }

    public static int getCardsCount(ContentResolver contentResolver){
        Cursor cursor = contentResolver.query(CardTable.CONTENT_URI, null,null,null,null);
        return cursor.getCount();
    }

    public static void initialPersistCardsInDatabase(ContentResolver contentResolver, Resources resources, Map<String, List<Card>> cardsMap) {

        for (List<Card> cards : cardsMap.values()) {
            for (Card card : cards) {


                CardSql sqlCard = CardSql.buildFromCard(card);
                contentResolver.insert(CardTable.CONTENT_URI, CardTable.getContentValues(sqlCard, true));
            }
        }
    }

    public static boolean urlExists(String URLName){
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con =
                    (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
