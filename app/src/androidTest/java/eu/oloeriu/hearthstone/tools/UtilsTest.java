package eu.oloeriu.hearthstone.tools;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Bogdan Oloeriu on 16/03/2018.
 */
public class UtilsTest {
    @Test
    public void loadCardsFromJson() throws Exception {
        Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        Utils.loadCardsFromJson(resources);
        //Resources resources = getInstru
        //Utils.loadCardsFromJson()
    }

}