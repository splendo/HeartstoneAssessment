package com.kapanen.hearthstoneassessment.model

import android.os.Parcelable
import com.kapanen.hearthstoneassessment.data.CardType
import kotlinx.parcelize.Parcelize

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
 * @param flavor
 * @param artist
 * @param elite
 * @param playerClass
 * @param multiClassGroup
 * @param img
 * @param imgGold
 * @param mechanics
 */
@Parcelize
data class Card(
    val cardId: String,
    val cardType: CardType? = null,
    val name: String? = null,
    val cardSet: String? = null,
    val type: String? = null,
    val faction: String? = null,
    val rarity: String? = null,
    val cost: Int? = null,
    val attack: Int? = null,
    val health: Int? = null,
    val htmlText: String? = null,
    val flavor: String? = null,
    val artist: String? = null,
    val collectible: Boolean? = null,
    val elite: Boolean? = null,
    val playerClass: String? = null,
    val multiClassGroup: String? = null,
    val img: String? = null,
    val imgGold: String? = null,
    val mechanics: List<Mechanic>? = null,
    val isFavorite: Boolean = false
) : Parcelable
