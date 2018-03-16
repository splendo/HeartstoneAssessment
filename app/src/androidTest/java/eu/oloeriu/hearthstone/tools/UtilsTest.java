package eu.oloeriu.hearthstone.tools;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Bogdan Oloeriu on 16/03/2018.
 */
public class UtilsTest {
    @Test
    public void loadCardsFromJson() throws Exception {
        Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        Map<String, List<Card>> map = Utils.loadCardsFromJson(resources);
        assertTrue("Contains basic", map.containsKey("Basic"));
        List<Card> basicCards = map.get("Basic");
        assertTrue("Contains more then 10 cards", basicCards.size() > 10);

        for (Card card : basicCards) {
            if (card.getCardId().equals("CS2_041e")) {
                assertTrue("Name not \"Ancestral Infusion\"", card.getName().equals("Ancestral Infusion"));
                Mechanic mechanic = card.getMechanics().get(0);
                assertTrue("It should be taunt", mechanic.getName().equals("Taunt"));
                return;
            }
        }
        fail("Wee never managed to locate the Ancestral infusion card");
    }

}