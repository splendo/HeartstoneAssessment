package eu.oloeriu.hearthstone.tools;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private static final String ACTION_BAZ = "eu.oloeriu.hearthstone.tools.action.BAZ";
    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "eu.oloeriu.hearthstone.tools.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "eu.oloeriu.hearthstone.tools.extra.PARAM2";
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
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INITIAL_SETUP.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionInitialSetup(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionInitialSetup(String param1, String param2) {
        // TODO: Handle action Foo

        String deviceId = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d(logTag, "deviceId = " + deviceId);

        String cardsLocation = Constants.LOCATION_CARDS
                .replace(Constants.DEVICE_ID, deviceId);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference cards = database.getReference(cardsLocation);
        DatabaseReference cardRef = cards.push();

        Card card = new Card();
        card.setName("First card");

        cardRef.setValue(card);

        //store cards in sqlLight
        //todo check that there are no cards in the database

        Map<String, List<Card>> jsonMap = Utils.loadCardsFromJson(this.getResources());

        if (Utils.getCardsCount(this.getContentResolver()) < 1000) {
            Log.d(logTag, "Initializing population for " + jsonMap.values().size() + " items");
            Utils.initialPersistCardsInDatabase(this.getContentResolver(), this.getResources(), jsonMap);
        }
        Log.d(logTag, "Wee have " + Utils.getCardsCount(this.getContentResolver()) + " cards in database");

        Intent mainActivityIntent = new Intent(getBaseContext(), MainActivity.class);
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(mainActivityIntent);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
