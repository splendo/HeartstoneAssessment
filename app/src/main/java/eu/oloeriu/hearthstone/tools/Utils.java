package eu.oloeriu.hearthstone.tools;

import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.oloeriu.hearthstone.R;

/**
 * Created by Bogdan Oloeriu on 15/03/2018.
 */

public class Utils {
    private static String logTag = Constants.LOG_TAG;



    public static Map<String, List<Card>> loadCardsFromJson(Resources resources) {


        String json = null;
        InputStream is = resources.openRawResource(R.raw.cards);
        Reader reader = new InputStreamReader(is);


        Type type = new TypeToken<Map<String, List<Card>>>() {
        }.getType();
        Gson gson = new Gson();
        Map<String, List<Card>> map = gson.fromJson(reader, type);
        return map;
    }
}
