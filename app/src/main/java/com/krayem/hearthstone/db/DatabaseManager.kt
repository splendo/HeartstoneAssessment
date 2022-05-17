package com.krayem.hearthstone.db

import com.krayem.hearthstone.model.Card
import com.krayem.hearthstone.model.SectionFilter

interface DatabaseManager{

    fun getAll(): List<Card>


    fun get(types: Array<String>): List<Card>

    fun populateDb()
    fun getCardSet(cardSet: String): List<Card>
    fun getTypes(): List<String>
    fun getClasses(): List<String>
    fun getMechanics(): List<String>
    fun clearFilters()
    fun getFilters():SectionFilter
    fun getFavourites(): List<Card>
    fun checkIsFavourite(cardId: String): Boolean
    fun toggleFavourite(cardId: String): Boolean
    fun isEmpty(): Boolean
    fun saveFilters(
        types: List<String>,
        mechanics: List<String>,
        classes: List<String>,
        minRarity: Int,
        maxRarity: Int,
        sortBy: Int,
        descending: Boolean
    )

    fun getCardClasses(cardId: String): Array<String>
    fun getCardMechanics(cardId: String): Array<String>
}