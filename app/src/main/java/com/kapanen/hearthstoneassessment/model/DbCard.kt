package com.kapanen.hearthstoneassessment.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class DbCard(
    @PrimaryKey @ColumnInfo(name = "cardId") val cardId: String,
    @ColumnInfo(name = "cardType") val cardType: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "cardSet") val cardSet: String? = null,
    @ColumnInfo(name = "type") val type: String? = null,
    @ColumnInfo(name = "faction") val faction: String? = null,
    @ColumnInfo(name = "rarity") val rarity: String? = null,
    @ColumnInfo(name = "cost") val cost: Int? = null,
    @ColumnInfo(name = "attack") val attack: Int? = null,
    @ColumnInfo(name = "health") val health: Int? = null,
    @ColumnInfo(name = "htmlText") val htmlText: String? = null,
    @ColumnInfo(name = "flavor") val flavor: String? = null,
    @ColumnInfo(name = "artist") val artist: String? = null,
    @ColumnInfo(name = "elite") val elite: Boolean? = null,
    @ColumnInfo(name = "collectible") val collectible: Boolean? = null,
    @ColumnInfo(name = "playerClass") val playerClass: String? = null,
    @ColumnInfo(name = "multiClassGroup") val multiClassGroup: String? = null,
    @ColumnInfo(name = "img") val img: String? = null,
    @ColumnInfo(name = "imgGold") val imgGold: String? = null,
    @ColumnInfo(name = "mechanics") val mechanics: String? = null,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean = false
)
