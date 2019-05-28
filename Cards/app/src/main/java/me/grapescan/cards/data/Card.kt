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
    val text: String,
    val flavor: String,
    val artist: String,
    val collectible: Boolean,
    val playerClass: String,
    val howToGetGold: String,
    val imageUrl: String,
    val imageGoldUrl: String,
    val mechanics: List<String>,
    val isFavorite: Boolean = false
) : Parcelable
