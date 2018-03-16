package eu.oloeriu.hearthstone.tools;

import android.content.res.Resources;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import eu.oloeriu.hearthstone.R;

/**
 * Created by Bogdan Oloeriu on 15/03/2018.
 */

public class Utils {
    private static String logTag = Constants.LOG_TAG;
    public static List<Card> loadCardsFromJson(Resources resources){


        String json = null;
        try {
            InputStream is = resources.openRawResource(R.raw.cards);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Log.d(logTag,json);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Log.d(logTag, "Initiate loadLocalCards()");
        return new ArrayList<>();
    }
}
