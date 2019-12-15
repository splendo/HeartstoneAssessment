package com.krayem.hearthstone.objectbox

import com.krayem.hearthstone.model.Mechanic
import io.objectbox.converter.PropertyConverter
import org.json.JSONArray

class ObjectBoxMechanicListConverter : PropertyConverter<List<Mechanic>, String> {
    override fun convertToDatabaseValue(entityProperty: List<Mechanic>?): String {
        val json = JSONArray()
        entityProperty?.forEach {
            json.put(it.name)
        }
        return json.toString()
    }

    override fun convertToEntityProperty(databaseValue: String?): List<Mechanic> {
        val json = JSONArray(databaseValue)
        val list = ArrayList<Mechanic>()
        for (i in 0 until json.length()) {
            list.add(Mechanic(json.getString(i)))
        }
        return list
    }
}