package me.grapescan.cards.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(
    val id: String,
    val name: String,
    val cardSet: String,
    val type: String,
    val rarity: String,
    val cost: Int,
    val attack: Int,
    val health: Int,
    val text: CharSequence,
    val flavor: String,
    val artist: String,
    val collectible: Boolean,
    val playerClass: String,
    val howToGetGold: String,
    val imageUrl: String,
    val imageGoldUrl: String,
    val mechanics: List<String>,
    val isFavorite: Boolean = false
) : Parcelable {
    override fun equals(other: Any?) = other is Card
            && id == other.id
            && name == other.name
            && cardSet == other.cardSet
            && type == other.type
            && rarity == other.rarity
            && cost == other.cost
            && attack == other.attack
            && health == other.health
            && text.toString() == other.text.toString()
            && flavor == other.flavor
            && artist == other.artist
            && collectible == other.collectible
            && playerClass == other.playerClass
            && howToGetGold == other.howToGetGold
            && imageUrl == other.imageUrl
            && imageGoldUrl == other.imageGoldUrl
            && mechanics == other.mechanics
            && isFavorite == other.isFavorite
}
