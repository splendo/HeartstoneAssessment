package eu.oloeriu.hearthstone.tools;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.oloeriu.hearthstone.ui.MainActivity;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_INITIAL_SETUP = "eu.oloeriu.hearthstone.tools.action.INITIAL_SETUP";
    private static final String ACTION_UPDATE_FAVORITE_CARD = "eu.oloeriu.hearthstone.tools.action.UPDATE_FAVORITE_CARD";
    // TODO: Rename parameters
    private static final String EXTRA_CARD_ID = "eu.oloeriu.hearthstone.tools.extra.PARAM1";
    private static final String EXTRA_CARD_FAVORITE_VALUE = "eu.oloeriu.hearthstone.tools.extra.PARAM2";
    private static String logTag = Constants.LOG_TAG;

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionInitialSetup(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_INITIAL_SETUP);
        intent.putExtra(EXTRA_CARD_ID, param1);
        intent.putExtra(EXTRA_CARD_FAVORITE_VALUE, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionUpdateFavoriteCard(Context context, String cardId, int favoriteValue) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_UPDATE_FAVORITE_CARD);
        intent.putExtra(EXTRA_CARD_ID, cardId);
        intent.putExtra(EXTRA_CARD_FAVORITE_VALUE, favoriteValue);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INITIAL_SETUP.equals(action)) {
                handleActionInitialSetup();
            } else if (ACTION_UPDATE_FAVORITE_CARD.equals(action)) {
                final String cardId = intent.getStringExtra(EXTRA_CARD_ID);
                final int favoriteValue = intent.getIntExtra(EXTRA_CARD_FAVORITE_VALUE, 0);
                handleActionUpdateFavoriteCard(cardId, favoriteValue);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionInitialSetup() {

        String deviceId = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d(logTag, "deviceId = " + deviceId);

        //store cards in sqlLight
        Map<String, List<Card>> jsonMap = Utils.loadCardsFromJson(this.getResources());

        if (Utils.getCardsCount(this.getContentResolver()) < 1000) {
            Log.d(logTag, "Initializing population for " + jsonMap.values().size() + " items");
            Utils.initialPersistCardsInDatabase(this.getContentResolver(), this.getResources(), jsonMap);
        }
        Log.d(logTag, "Wee have " + Utils.getCardsCount(this.getContentResolver()) + " cards in database");

        //todo consider querring the apy for owned cards for a moment this is just a raw test
//        List<Card> meanStreats = jsonMap.get("Mean Streets of Gadgetzan");
//        for (Card card : meanStreats) {
//            if (card.getCardId().equals("CFM_902")) {
//                CardSql cardSql = CardSql.buildFromCard(card);
//                Utils.updateCardInFirebase(deviceId, cardSql);
//            }
//        }

        //setup shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        List<Card> cards = new ArrayList<>();
        for (List<Card> cardSet : jsonMap.values()) {
            cards.addAll(cardSet);
        }
        Utils.setupSharedSets(sharedPreferences, cards);

        Intent mainActivityIntent = new Intent(getBaseContext(), MainActivity.class);
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(mainActivityIntent);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdateFavoriteCard(String cardId, int favoriteValue) {
        //Time to update the card in SQLLight
        Utils.updateFavorite(cardId, favoriteValue, this.getContentResolver());
    }
}
