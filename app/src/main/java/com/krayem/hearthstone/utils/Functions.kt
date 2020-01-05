package com.krayem.hearthstone.utils

import com.krayem.hearthstone.model.Mechanic
import org.json.JSONArray
import org.json.JSONObject

fun toJsonObject(types: Set<String>,classes: Set<String>, mechanics: Set<String>,rarity: Pair<Int,Int>) : JSONObject{
    val json = JSONObject()

    val typesJSONArray = JSONArray()
    for (item in types) {
        typesJSONArray.put(item)
    }
    json.put("types",typesJSONArray)

    val classesJSONArray = JSONArray()
    for (item in classes) {
        classesJSONArray.put(item)
    }
    json.put("classes",classesJSONArray)

    val mechanicsJSONArray = JSONArray()
    for (item in mechanics) {
        mechanicsJSONArray.put(item)
    }
    json.put("mechanics",mechanicsJSONArray)

    json.put("minRarity",rarity.first)
    json.put("maxRarity",rarity.second)

    return json
}

fun fromJsonArray(json: JSONArray) : MutableSet<String>{
    val set = mutableSetOf<String>()
    for (i in 0 until json.length()){
        set.add(json.getString(i))
    }
    return set
}

fun fromJsonArrayToArrayList(json: JSONArray) : List<String>{
    val set = arrayListOf<String>()
    for (i in 0 until json.length()){
        set.add(json.getString(i))
    }
    return set
}

fun fromJsonArrayToMechanicsList(json: JSONArray) : List<Mechanic>{
    val set = arrayListOf<Mechanic>()
    for (i in 0 until json.length()){
        set.add(Mechanic(json.getJSONObject(i).getString("name")))
    }
    return set
}

fun getRarityPair(json: JSONObject): Pair<Int,Int>{
    return Pair(json.getInt("minRarity"),json.getInt("maxRarity"))
}