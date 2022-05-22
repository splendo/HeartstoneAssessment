package com.kapanen.hearthstoneassessment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kapanen.hearthstoneassessment.model.DbCard

@Database(entities = [DbCard::class], version = 1, exportSchema = false)
abstract class HearthstoneDatabase : RoomDatabase() {

    abstract fun cardsDao(): CardsDao

}
