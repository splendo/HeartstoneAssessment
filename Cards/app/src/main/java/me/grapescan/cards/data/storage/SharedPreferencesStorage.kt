package me.grapescan.cards.data.storage

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager

class PersistentFavoritesStorage(context: Context) : Storage<List<String>> {

    companion object {
        private const val PREF_FAVORITES = "favorites"
    }

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    override suspend fun load() = prefs.getStringSet(PREF_FAVORITES, emptySet())?.toList() ?: emptyList()

    @SuppressLint("ApplySharedPref")
    override suspend fun save(data: List<String>) {
        prefs.edit().putStringSet(PREF_FAVORITES, data.toSet()).commit()
    }

}
