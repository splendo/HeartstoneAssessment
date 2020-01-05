package com.krayem.hearthstone.objectbox

import io.objectbox.converter.PropertyConverter
import org.json.JSONArray

class ObjectBoxStringListConverter : PropertyConverter<List<String>,String> {


    override fun convertToDatabaseValue(entityProperty: List<String>?): String {
        val json = JSONArray()
        entityProperty?.forEach {
            json.put(it)
        }
        return json.toString()
    }

    override fun convertToEntityProperty(databaseValue: String?): List<String> {
        val json = JSONArray(databaseValue)
        val list = ArrayList<String>()
        for(i in 0 until json.length()){
            list.add(json.getString(i))
        }
        return list
    }
}