package com.krayem.hearthstone.db

import android.content.Context
import android.util.Log
import com.krayem.hearthstone.App
import com.krayem.hearthstone.R
import com.krayem.hearthstone.model.*
import com.krayem.hearthstone.objectbox.ObjectBox
import com.krayem.hearthstone.objectbox.ObjectBoxStringListConverter
import com.krayem.hearthstone.utils.fromJsonArrayToArrayList
import com.krayem.hearthstone.utils.fromJsonArrayToMechanicsList
import io.objectbox.Box
import io.objectbox.annotation.Convert
import io.objectbox.kotlin.boxFor
import org.json.JSONArray
import org.json.JSONObject

open class DefaultDatabaseManager : DatabaseManager {


    override fun getAll(): List<Card> {
        val cardBox: Box<Card> = ObjectBox.boxStore.boxFor()
        return cardBox.all
    }

    override fun saveFilters(types: List<String>, mechanics: List<String>, classes: List<String>, minRarity: Int, maxRarity: Int, sortBy: Int, descending: Boolean) {
        val filtersBox: Box<SectionFilter> = ObjectBox.boxStore.boxFor()
        filtersBox.removeAll()
        filtersBox.put(SectionFilter(0,types, mechanics, classes, minRarity, maxRarity, sortBy, descending))
    }

    private fun getCardsFromJSON(jsonArray: JSONArray): MutableList<Card> {
        val toReturn = mutableListOf<Card>()
        for (i in 0 until jsonArray.length()) {
            val cardJson = jsonArray.getJSONObject(i)
            val current = Card(
                0,
                if (cardJson.has("cardId")) cardJson.getString("cardId") else null,
                if (cardJson.has("name")) cardJson.getString("name") else "",
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
                if (cardJson.has("classes")) fromJsonArrayToArrayList(JSONArray(cardJson.getString("classes"))) else listOf(),
                if (cardJson.has("howToGet")) cardJson.getString("howToGet") else null,
                if (cardJson.has("howToGetGold")) cardJson.getString("howToGetGold") else null,
                if (cardJson.has("img")) cardJson.getString("img") else null,
                if (cardJson.has("imgGold")) cardJson.getString("imgGold") else null,
                if (cardJson.has("locale")) cardJson.getString("locale") else null,
                if (cardJson.has("mechanics")) fromJsonArrayToMechanicsList(
                    JSONArray(
                        cardJson.getString(
                            "mechanics"
                        )
                    )
                ) else listOf()
            )
            toReturn.add(current)
        }
        handleClassesAndMechanics(toReturn)
        return toReturn
    }


    override fun isEmpty(): Boolean {
        return ObjectBox.boxStore.boxFor(Card::class.java).isEmpty
    }

    override fun clearFilters() {
        val filtersBox: Box<SectionFilter> = ObjectBox.boxStore.boxFor()
        filtersBox.removeAll()
    }

    override fun getFilters(): SectionFilter {
        val filtersBox: Box<SectionFilter> = ObjectBox.boxStore.boxFor()
        var filter = filtersBox.query().build().findFirst()
        if(filter == null){
            filter = SectionFilter(0, listOf(), listOf(), listOf(),0,4,SectionFilter.SORT_BY_ALPHABETICAL,false)
            filtersBox.put(filter)
        }
        return filter
    }

    override fun getCardClasses(cardId: String):Array<String>{
        val classesBox:Box<CardClass> = ObjectBox.boxStore.boxFor()
        return classesBox.query().equal(CardClass_.cardId,cardId).build().property(CardClass_.name).findStrings()
    }

    override fun getCardMechanics(cardId: String):Array<String>{
        val mechanicsBox:Box<CardMechanic> = ObjectBox.boxStore.boxFor()
        return mechanicsBox.query().equal(CardMechanic_.cardId,cardId).build().property(CardMechanic_.name).findStrings()
    }

    override fun getCardSet(cardSet: String): List<Card> {
        val cardBox: Box<Card> = ObjectBox.boxStore.boxFor()

        val filtersBox: Box<SectionFilter> = ObjectBox.boxStore.boxFor()
        val filter = filtersBox.query().build().findFirst()
        val queryBuilder = cardBox.query().equal(Card_.cardSet, cardSet)

        if (filter != null) {
            val selectedTypes = filter.types.toTypedArray()
            val selectedClasses = filter.classes.toTypedArray()
            val selectedMechanics = filter.mechanics.toTypedArray()
            val selectedRarities = mutableListOf<String>()
            val allRarities = App.instance.resources.getStringArray(R.array.rarity_array)
            for (i in filter.minRarity until filter.maxRarity + 1) {
                selectedRarities.add(allRarities[i])
            }
            if (selectedTypes.isNotEmpty()) {
                queryBuilder.`in`(Card_.type, selectedTypes)
            }

            if (selectedClasses.isNotEmpty()) {
                val classBox: Box<CardClass> = ObjectBox.boxStore.boxFor()
                val idsFromClasses = classBox.query().`in`(CardClass_.name, selectedClasses).build()
                    .property(CardClass_.cardId).findStrings()
                queryBuilder.`in`(Card_.cardId, idsFromClasses)
            }

            if (selectedMechanics.isNotEmpty()) {
                val classBox: Box<CardMechanic> = ObjectBox.boxStore.boxFor()
                val idsFromMechanics =
                    classBox.query().`in`(CardMechanic_.name, selectedMechanics).build()
                        .property(CardMechanic_.cardId).findStrings()
                queryBuilder.`in`(Card_.cardId, idsFromMechanics)
            }

            if (selectedRarities.isNotEmpty()) {
                queryBuilder.`in`(Card_.rarity, selectedRarities.toTypedArray())
            }
            if (filter.sortBy == SectionFilter.SORT_BY_RARITY) {
                queryBuilder.sort { a: Card, b: Card ->
                    if (filter.descending) {
                        allRarities.indexOf(b.rarity).compareTo(allRarities.indexOf(a.rarity))
                    } else {
                        allRarities.indexOf(a.rarity).compareTo(allRarities.indexOf(b.rarity))
                    }
                }
            } else {
                if (filter.descending) {
                    queryBuilder.orderDesc(Card_.name)
                } else {
                    queryBuilder.order(Card_.name)
                }
            }
        }
        return queryBuilder.build().find()
    }

    override fun getFavourites(): List<Card> {
        val cardBox: Box<Card> = ObjectBox.boxStore.boxFor()
        val favouritesBox: Box<FavouriteCard> = ObjectBox.boxStore.boxFor()
        return cardBox.query().`in`(
            Card_.cardId,
            favouritesBox.query().build().property(FavouriteCard_.cardId).distinct().findStrings()
        ).build().find()
    }

    override fun get(types: Array<String>): List<Card> {
        val cardBox: Box<Card> = ObjectBox.boxStore.boxFor()
        return when (types.isEmpty()) {
            true -> cardBox.query().build().find()
            false -> cardBox.query().`in`(Card_.type, types).build().find()
        }
    }

    override fun getTypes(): List<String> {
        val cardBox: Box<Card> = ObjectBox.boxStore.boxFor()
        cardBox.query().build().property(Card_.rarity).distinct().findStrings().forEach {
            Log.e("rarity", it)
        }
        return cardBox.query().build().property(Card_.type).distinct().findStrings().asList()
    }

    override fun getClasses(): List<String> {
        val cardBox: Box<CardClass> = ObjectBox.boxStore.boxFor()
        return cardBox.query().build().property(CardClass_.name).distinct().findStrings().asList()
    }

    override fun getMechanics(): List<String> {
        val cardBox: Box<CardMechanic> = ObjectBox.boxStore.boxFor()
        return cardBox.query().build().property(CardMechanic_.name).distinct().findStrings()
            .asList()
    }

    private fun handleClassesAndMechanics(cards: List<Card>) {
        val classBox: Box<CardClass> = ObjectBox.boxStore.boxFor()
        val mechanicBox: Box<CardMechanic> = ObjectBox.boxStore.boxFor()
        cards.forEach { card ->
            if (card.classes != null && card.classes.isNotEmpty()) {
                card.classes.forEach { oneClass ->
                    card.cardId?.let {
                        classBox.put(CardClass(0, card.cardId, oneClass))
                    }
                }
            }
            if (card.mechanics != null && card.mechanics.isNotEmpty()) {
                card.mechanics.forEach { oneMechanic ->
                    card.cardId?.let {
                        mechanicBox.put(CardMechanic(0, card.cardId, oneMechanic.name))
                    }
                }
            }
        }
    }

    override fun checkIsFavourite(cardId: String): Boolean {
        val box: Box<FavouriteCard> = ObjectBox.boxStore.boxFor()

        return box.query().equal(FavouriteCard_.cardId, cardId).build().count() > 0

    }

    override fun toggleFavourite(cardId: String): Boolean {
        val box: Box<FavouriteCard> = ObjectBox.boxStore.boxFor()
        val query = box.query().equal(FavouriteCard_.cardId, cardId).build()
        return if (query.count() > 0) {
            query.remove()
            false
        } else {
            box.put(FavouriteCard(0, cardId))
            true
        }
    }

    override fun populateDb() {
        Log.e("db", "started reading file")
        val jsonfile: String =
            App.instance.assets.open("cards.json").bufferedReader().use { it.readText() }

        Log.e("db", "started converting data")
        val all = JSONObject(jsonfile)
        val toInsert = mutableListOf<Card>()
//        val basics =
//        handleClasses(basics)

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