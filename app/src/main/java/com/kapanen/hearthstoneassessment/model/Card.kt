package com.kapanen.hearthstoneassessment.model

import androidx.room.Entity
import com.kapanen.hearthstoneassessment.data.CardType

/**
 *
 * @param cardId
 * @param cardType
 * @param name
 * @param cardSet
 * @param type
 * @param rarity
 * @param cost
 * @param attack
 * @param health
 * @param htmlText
 * @param flavour
 * @param artist
 * @param elite
 * @param playerClass
 * @param multiClassGroup
 * @param img
 * @param imgGold
 * @param mechanics
 */
data class Card(
    val cardId: String,
    val cardType: CardType? = null,
    val name: String? = null,
    val cardSet: String? = null,
    val type: String? = null,
    val rarity: String? = null,
    val cost: Int? = null,
    val attack: Int? = null,
    val health: Int? = null,
    val htmlText: String? = null,
    val flavour: String? = null,
    val artist: String? = null,
    val elite: Boolean? = null,
    val playerClass: String? = null,
    val multiClassGroup: String? = null,
    val img: String? = null,
    val imgGold: String? = null,
    val mechanics: List<Mechanic>? = null,
    val isFavorite: Boolean = false
)