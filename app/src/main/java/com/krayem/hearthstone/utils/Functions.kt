package com.krayem.hearthstone.utils

import org.json.JSONArray

fun toJsonArray(set: Set<String>) : JSONArray{
    val json = JSONArray()
    for (item in set) {
        json.put(item)
    }
    return json
}

fun fromJsonArray(json: JSONArray) : MutableSet<String>{
    val set = mutableSetOf<String>()
    for (i in 0 until json.length()){
        set.add(json.getString(i))
    }
    return set
}