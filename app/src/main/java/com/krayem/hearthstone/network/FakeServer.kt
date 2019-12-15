package com.krayem.hearthstone.network

import android.util.Log
import com.krayem.hearthstone.App
import com.krayem.hearthstone.db.DatabaseManager
import com.krayem.hearthstone.db.DatabaseModule
import com.krayem.hearthstone.di.DaggerComponentInjector
import com.krayem.hearthstone.model.Card
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class FakeServer {

    @Inject
    lateinit var databaseManager: DatabaseManager

    init {
        DaggerComponentInjector
            .builder()
            .databaseModule(DatabaseModule)
            .build()
            .inject(this)
    }


    fun getAll(): JSONArray {
        val responseJson = JSONArray()
        databaseManager.get("Tavern Brawl").forEach {
            val currentJson = JSONObject()
            currentJson.put("cardId",it.cardId)
            currentJson.put("name",it.name)
            currentJson.put("cardSet",it.cardSet)
            currentJson.put("type",it.type)
            currentJson.put("rarity",it.rarity)
            currentJson.put("cost",it.cost)
            currentJson.put("attack",it.attack)
            currentJson.put("health",it.health)
            currentJson.put("text",it.text)
            currentJson.put("flavor",it.flavor)
            currentJson.put("artist",it.artist)
            currentJson.put("collectible",it.collectible)
            currentJson.put("elite",it.elite)
            currentJson.put("playerClass",it.playerClass)
            currentJson.put("multiClassGroup",it.multiClassGroup)
            currentJson.put("howToGet",it.howToGet)
            currentJson.put("howToGetGold",it.howToGetGold)
            currentJson.put("img",it.img)
            currentJson.put("imgGold",it.imgGold)
            currentJson.put("locale",it.locale)
            responseJson.put(currentJson)
        }
        Log.e("getAll",responseJson.length().toString())
        return responseJson
    }

    fun get(types: Array<String>): List<Card> {
        return databaseManager.get(types)
    }

}