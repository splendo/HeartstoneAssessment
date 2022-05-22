package com.kapanen.hearthstoneassessment.setting

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonParseException
import java.io.IOException
import java.lang.reflect.Type

@Suppress("SameParameterValue")
class SharedPreferencesStorage(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : Storage {

    override fun <T> set(key: String, value: T?): Boolean {
        if (value == null) {
            return sharedPreferences.edit().remove(key).commit()
        }
        return sharedPreferences.edit().putString(key, serializeData(value)).commit()
    }


    override fun <T> get(key: String, clazz: Class<T>, defaultValue: T?): T? {
        val value: T?
        try {
            value = getCommon(key, clazz, null)
        } catch (e: IOException) {
            return defaultValue
        } catch (e: ClassCastException) {
            return defaultValue
        } catch (e: JsonParseException) {
            return defaultValue
        }

        return value ?: defaultValue
    }

    @Throws(IOException::class)
    private fun <T> getCommon(
        key: String,
        clazz: Class<T>?,
        type: Type?
    ): T? {
        val data = sharedPreferences.getString(key, null) ?: return null
        if (clazz != null) {
            return deserializeData(data, clazz)
        } else if (type != null) {
            return deserializeData<T>(data, type)
        }
        return null
    }

    private fun <T> serializeData(value: T): String {
        return gson.toJson(value)
    }

    @Throws(IOException::class)
    private fun <T> deserializeData(data: String, clazz: Class<T>): T {
        return gson.fromJson(data, clazz)
    }

    @Throws(IOException::class)
    private fun <T> deserializeData(data: String, type: Type): T {
        return gson.fromJson<T>(data, type)
    }

}
