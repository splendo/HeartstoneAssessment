package eu.oloeriu.hearthstone.tools;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.oloeriu.hearthstone.TestingUtils;
import eu.oloeriu.hearthstone.data.CardSql;
import eu.oloeriu.hearthstone.data.CardTable;

import static org.junit.Assert.*;

/**
 * Created by Bogdan Oloeriu on 19/03/2018.
 */
public class TestUtils {
    @Test
    public void loadCardsFromJson() throws Exception {
        Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        Map<String, List<Card>> map = Utils.loadCardsFromJson(resources);
        assertTrue("Contains basic", map.containsKey("Basic"));
        List<Card> basicCards = map.get("Basic");
        assertTrue("Contains more then 10 cards", basicCards.size() > 10);


        boolean cardFound = false;
        for (Card card : basicCards) {
            if (card.getCardId().equals("hexfrog")) {
                assertTrue("Name not \"Frog\"", card.getName().equals("Frog"));
                Mechanic mechanic = card.getMechanics().get(0);
                assertTrue("It should be taunt", mechanic.getName().equals("Taunt"));
                cardFound = true;
                break;
            }
        }
        assertTrue("Wee never managed to locate the Ancestral infusion card", cardFound);

        cardFound = false;
        List<Card> streetsOfGadzetzan = map.get("Mean Streets of Gadgetzan");
        for (Card card : streetsOfGadzetzan) {
            if (card.getCardId().equals("CFM_902")) {

                String[] classes = {"Druid", "Rogue", "Shaman"};
                List<String> myClasses = card.getClasses();
                for (String item : classes) {
                    assertTrue("Class " + item + " not found", myClasses.contains(item));
                }
                cardFound = true;
                break;
            }
        }
        assertTrue("Wee never managed to locate Aya Blackpaw", cardFound);
    }

    @Test
    public void getCardsCount() throws Exception {
        ContentResolver contentResolver = InstrumentationRegistry.getTargetContext().getContentResolver();

        TestingUtils.deleteAllRecordsFromProvider(contentResolver);

        CardSql cardSql = new CardSql();
        cardSql.setName("Bogdan");

        //insert 3 cards
        for (int i = 1; i <= 3; i++) {

            cardSql.setCardId(String.valueOf(i));
            contentResolver.insert(CardTable.CONTENT_URI, CardTable.getContentValues(cardSql, true));
        }


        int count = Utils.getCardsCount(contentResolver);
        assertEquals("Not the expected count", 3, count);

        TestingUtils.deleteAllRecordsFromProvider(contentResolver);
    }

    @Test
    public void initialPersistCardsInDatabase() throws Exception {
        Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        Map<String, List<Card>> map = Utils.loadCardsFromJson(resources);
        int totalCards = 0; // should be 3116 cards (quite allot of them)
        for (List<Card> cardsSet : map.values()) {
            totalCards += cardsSet.size();
        }
        System.out.println("Total cards = " + totalCards);

        ContentResolver contentResolver = InstrumentationRegistry.getTargetContext().getContentResolver();

        TestingUtils.deleteAllRecordsFromProvider(contentResolver);

        Map<String, List<Card>> allMap = Utils.loadCardsFromJson(resources);

        Utils.initialPersistCardsInDatabase(contentResolver, resources, allMap);

        //get all cards cursor
        Cursor cursor = contentResolver.query(CardTable.CONTENT_URI, null, null, null, null);
        int databaseCount = cursor.getCount();
        assertEquals("Not same number of cards in database", totalCards, databaseCount);

        //clean things out
        TestingUtils.deleteAllRecordsFromProvider(contentResolver);
    }

    @Test
    public void setupSharedSets() throws Exception {
        Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        Map<String, List<Card>> map = Utils.loadCardsFromJson(resources);
        List<Card> cards = new ArrayList<>();
        for (List<Card> setList : map.values()){
            cards.addAll(setList);
        }

        SharedPreferences sharedPreferences = InstrumentationRegistry.getContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);

        Utils.setupSharedSets(sharedPreferences, cards);
        Set<String> types = sharedPreferences.getStringSet(Constants.SHARED_SET_TYPE, null);
        Set<String> rarityes = sharedPreferences.getStringSet(Constants.SHARED_SET_RARITIES, null);
        Set<String> classes = sharedPreferences.getStringSet(Constants.SHARED_SET_CLASSES,null);
        Set<String> mechanics = sharedPreferences.getStringSet(Constants.SHARED_SET_MECHANICS, null);
        Set<String> card_sets = sharedPreferences.getStringSet(Constants.SHARED_SET_CARD_SETS, null);


        assertTrue("Types ware not initiated", types.size()>0);
        assertTrue("No Hero Power type", types.contains("Hero Power"));
        assertTrue("No Legendary rarity", rarityes.contains("Legendary"));
        assertTrue("No Warlock class", classes.contains("Warlock"));
        assertTrue("No Stealth mechanic", mechanics.contains("Stealth"));
        assertTrue("No Tavern Brawl card set", card_sets.contains("Tavern Brawl"));




    }

}