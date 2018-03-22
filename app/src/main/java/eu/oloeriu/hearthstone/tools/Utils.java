package eu.oloeriu.hearthstone.tools;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import java.util.Set;
import java.util.TreeSet;

import eu.oloeriu.hearthstone.R;
import eu.oloeriu.hearthstone.data.CardSql;
import eu.oloeriu.hearthstone.data.CardTable;

/**
 * Created by Bogdan Oloeriu on 15/03/2018.
 */

public class Utils {
    private static String logTag = Constants.LOG_TAG;


    /**
     * It loads all the cards from the clean_cards resource file
     *
     * @param resources the application context
     * @return a map of card sets names and card list
     */
    public static Map<String, List<Card>> loadCardsFromJson(Resources resources) {

        InputStream is = resources.openRawResource(R.raw.clean_gold_cards);
        Reader reader = new InputStreamReader(is);

        Type type = new TypeToken<Map<String, List<Card>>>() {
        }.getType();
        Gson gson = new Gson();
        Map<String, List<Card>> map = gson.fromJson(reader, type);
        return map;
    }

    /**
     * It counts the number of cards from the database
     *
     * @param contentResolver of the application
     * @return the number of cards from the database
     */
    public static int getCardsCount(ContentResolver contentResolver) {
        Cursor cursor = contentResolver.query(CardTable.CONTENT_URI, null, null, null, null);
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

    /**
     * Utility method to check if a specific http url exists.
     *
     * @param urlString the url string
     * @return
     */
    public static boolean urlExists(String urlString) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con =
                    (HttpURLConnection) new URL(urlString).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Meant to be used at start time to update shared preferences with possible
     * filter values from the cards.
     *
     * @param sharedPreferences the {@link SharedPreferences} used by the app
     * @param cards             the list of cards that will be scanned for possible filer values
     */
    public static void setupSharedSets(SharedPreferences sharedPreferences, List<Card> cards) {
        SharedPreferences.Editor editor = sharedPreferences.edit();


        Set<String> shared_type = new TreeSet<>();
        Set<String> shared_rarity = new TreeSet<>();
        Set<String> shared_classes = new TreeSet<>();
        Set<String> shared_mechanics = new TreeSet<>();
        Set<String> shared_card_sets = new TreeSet<>();
        for (Card card : cards) {
            String type = card.getType();
            if (type != null && !type.isEmpty() && !shared_type.contains(type)) {
                shared_type.add(card.getType());
            }

            String rarity = card.getRarity();
            if (rarity != null && !rarity.isEmpty() && !shared_rarity.contains(rarity)) {
                shared_rarity.add(rarity);
            }

            List<String> classes = card.getClasses();
            if (classes != null) {
                for (String cardClass : classes) {
                    if (cardClass != null && !cardClass.isEmpty() && !shared_classes.contains(cardClass)) {
                        shared_classes.add(cardClass);
                    }
                }
            }

            List<Mechanic> mechanics = card.getMechanics();
            if (mechanics != null) {
                for (Mechanic mechanic : mechanics) {
                    String name = mechanic.getName();
                    if (name != null && !name.isEmpty() && !shared_mechanics.contains(name)) {
                        shared_mechanics.add(name);
                    }
                }
            }

            String cardSet = card.getCardSet();
            if (cardSet != null && !cardSet.isEmpty() && !shared_card_sets.contains(cardSet)) {
                shared_card_sets.add(cardSet);
            }

        }

        editor.putStringSet(Constants.SHARED_SET_TYPE, shared_type);
        editor.putStringSet(Constants.SHARED_SET_RARITIES, shared_rarity);
        editor.putStringSet(Constants.SHARED_SET_CLASSES, shared_classes);
        editor.putStringSet(Constants.SHARED_SET_MECHANICS, shared_mechanics);
        editor.putStringSet(Constants.SHARED_SET_CARD_SETS, shared_card_sets);


        editor.apply();
    }


    /**
     * This one did not got the chance to use it. the application is wired up with firebase
     * but for the moment I do not store cards information in it. Word of notice to the evaluator:
     * "If the app is not able to initialize please send me the signature of you debug key so
     * I can update the firebase project that supports this app
     *
     * @param deviceId the phone ore table device id
     * @param cardSql  the {@link CardSql} that will be updated in the cloud
     */
    public static void updateCardInFirebase(String deviceId, CardSql cardSql) {
        String cardsLocation = Constants.LOCATION_CARD
                .replace(Constants.DEVICE_ID, deviceId)
                .replace(Constants.CARD_ID, cardSql.getCardId());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(cardsLocation);
        reference.setValue(cardSql);
    }

    /**
     * It updates the favorite information the card in SQLLight database
     *
     * @param cardId          the id of the card
     * @param favorite        the new favorite value
     * @param contentResolver the application content resolver
     */
    public static void updateFavorite(String cardId, int favorite, ContentResolver contentResolver) {
        Uri cardUri = CardTable.CONTENT_URI;

        ContentValues favoriteValues = new ContentValues();
        favoriteValues.put(CardTable.FIELD_CARDSFAVORITE, favorite);

        String selection = CardTable.FIELD__ID + " =? ";
        String selectionArgs[] = {cardId};

        int rowsUpdated = contentResolver.update(cardUri, favoriteValues, selection, selectionArgs);
        if (rowsUpdated != 1) {
            Log.e(logTag, "Huston wee have a problem. Not able to update one card " +
                    "Wee updated " + rowsUpdated + " cards");
        }

        Log.d(logTag, "update favorite initiated");
    }
}
