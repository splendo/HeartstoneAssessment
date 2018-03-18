package eu.oloeriu.hearthstone.tools;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Bogdan Oloeriu on 18/03/2018.
 */
public class UtilsTest {
    @Test
    public void urlExists() throws Exception {
        String url = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/CFM_902.png";
        assertTrue("Url not ok", Utils.urlExists(url));

        url = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/CFM_902_bogdan.png";
        assertFalse("Url not ok", Utils.urlExists(url));

    }

}