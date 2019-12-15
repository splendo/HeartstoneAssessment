package com.krayem.hearthstone.db

import android.util.Log
import com.krayem.hearthstone.App
import com.krayem.hearthstone.model.Card
import com.krayem.hearthstone.model.Card_
import com.krayem.hearthstone.objectbox.ObjectBox
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import org.json.JSONArray
import org.json.JSONObject

class DefaultDatabaseManager : DatabaseManager {

    override fun getAll(): List<Card> {
        populateIfEmpty()
        val cardBox: Box<Card> = ObjectBox.boxStore.boxFor()
        return cardBox.all
    }

    private fun getCardsFromJSON(jsonArray: JSONArray): MutableList<Card> {
        val toReturn = mutableListOf<Card>()
        for (i in 0 until jsonArray.length()) {
            val cardJson = jsonArray.getJSONObject(i)
            val current = Card(
                0,
                if (cardJson.has("cardId")) cardJson.getString("cardId") else null,
                if (cardJson.has("name")) cardJson.getString("name") else null,
                if (cardJson.has("cardSet")) cardJson.getString("cardSet") else null,
                if (cardJson.has("type")) cardJson.getString("type") else null,
                if (cardJson.has("rarity")) cardJson.getString("rarity") else null,
                if (cardJson.has("cost")) cardJson.getInt("cost") else null,
                if (cardJson.has("attack")) cardJson.getInt("attack") else null,
                if (cardJson.has("health")) cardJson.getInt("health") else null,
                if (cardJson.has("text")) cardJson.getString("text") else null,
                if (cardJson.has("flavor")) cardJson.getString("flavor") else null,
                if (cardJson.has("artist")) cardJson.getString("artist") else null,
                if (cardJson.has("collectible")) cardJson.getBoolean("collectible") else false,
                if (cardJson.has("elite")) cardJson.getBoolean("elite") else false,
                if (cardJson.has("playerClass")) cardJson.getString("playerClass") else null,
                if (cardJson.has("multiClassGroup")) cardJson.getString("multiClassGroup") else null,
                ArrayList(),
//                            todo classes
                if (cardJson.has("howToGet")) cardJson.getString("howToGet") else null,
                if (cardJson.has("howToGetGold")) cardJson.getString("howToGetGold") else null,
                if (cardJson.has("img")) cardJson.getString("img") else null,
                if (cardJson.has("imgGold")) cardJson.getString("imgGold") else null,
                if (cardJson.has("locale")) cardJson.getString("locale") else null,
                ArrayList()
                // todo mechanics
            )
            toReturn.add(current)
        }
        return toReturn
    }


    override fun populateIfEmpty() {
        val cardBox: Box<Card> = ObjectBox.boxStore.boxFor()
        if (cardBox.isEmpty) {
            Log.e("db","populating")
            populateDb()
        }else{
            Log.e("db",cardBox.count().toString())

        }
    }

    override fun get(cardSet:String):List<Card>{
        populateIfEmpty()
        val cardBox: Box<Card> = ObjectBox.boxStore.boxFor()
        return cardBox.query().equal(Card_.cardSet,cardSet).build().find()
    }
    override fun get(types: Array<String>): List<Card> {
        populateIfEmpty()
        val cardBox: Box<Card> = ObjectBox.boxStore.boxFor()
        return when (types.isEmpty()) {
            true -> cardBox.query().build().find()
            false -> cardBox.query().`in`(Card_.type, types).build().find()
        }

    }

    override fun populateDb() {
        Log.e("db", "started reading file")
        val jsonfile: String =
            App.instance.assets.open("cards.json").bufferedReader().use { it.readText() }

        Log.e("db", "started converting data")
        val all = JSONObject(jsonfile)
        val toInsert = mutableListOf<Card>()
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Basic")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Classic")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Promo")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Hall of Fame")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Naxxramas")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Goblins vs Gnomes")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Blackrock Mountain")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("The Grand Tournament")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("The League of Explorers")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Whispers of the Old Gods")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("One Night in Karazhan")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Mean Streets of Gadgetzan")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Journey to Un'Goro")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Tavern Brawl")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Hero Skins")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Missions")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Credits")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("System")))
        toInsert.addAll(getCardsFromJSON(all.getJSONArray("Debug")))

        Log.e("db", "started inserting")
        val box: Box<Card> = ObjectBox.boxStore.boxFor()
        box.put(toInsert)
        Log.e("db", "finished inserting")
    }

}