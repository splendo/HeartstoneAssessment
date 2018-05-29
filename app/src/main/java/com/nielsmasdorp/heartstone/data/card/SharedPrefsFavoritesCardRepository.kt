package com.nielsmasdorp.heartstone.data.card

import android.content.Context
import android.content.SharedPreferences
import com.nielsmasdorp.domain.card.data.FavoriteCardsRepository
import com.nielsmasdorp.heartstone.generic.dagger.AppContext
import javax.inject.Inject

/**
 * [FavoriteCardsRepository] implementation that stores the favorite ids in Shared Preferences
 */
class SharedPrefsFavoritesCardRepository @Inject constructor(@AppContext context: Context) : FavoriteCardsRepository {

    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(CARD_FAVORITES_STORAGE, Context.MODE_PRIVATE)
    }

    override fun addToFavorites(cardId: String) {
        sharedPrefs.getStringSet(CARD_FAVORITES_KEY, mutableSetOf()).apply {
            add(cardId)
            sharedPrefs.edit().putStringSet(CARD_FAVORITES_KEY, this).apply()
        }
    }

    override fun removeFromFavorites(cardId: String) {
        sharedPrefs.getStringSet(CARD_FAVORITES_KEY, mutableSetOf()).apply {
            remove(cardId)
            sharedPrefs.edit().putStringSet(CARD_FAVORITES_KEY, this).apply()
        }
    }

    override fun isAddedToFavorites(cardId: String): Boolean {
        return sharedPrefs.getStringSet(CARD_FAVORITES_KEY, emptySet()).contains(cardId)
    }

    private companion object {

        private const val CARD_FAVORITES_STORAGE = "CARD_FAVORITES_STORAGE"
        private const val CARD_FAVORITES_KEY = "CARD_FAVORITES"
    }
}