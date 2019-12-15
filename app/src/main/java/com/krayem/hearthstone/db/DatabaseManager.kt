package com.krayem.hearthstone.db

import com.krayem.hearthstone.model.Card

interface DatabaseManager{

    fun getAll(): List<Card>


    fun get(types: Array<String>): List<Card>

    fun populateIfEmpty()
    fun populateDb()
    fun get(cardSet: String): List<Card>
}