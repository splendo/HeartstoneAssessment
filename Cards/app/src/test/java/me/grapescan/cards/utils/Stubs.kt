package me.grapescan.cards.utils

import me.grapescan.cards.data.Card

fun card(
    id: String, name: String = "", cardSet: String = "", type: String = "", rarity: String = "",
    cost: Int = 0, attack: Int = 0, health: Int = 0, text: String = "", flavor: String = "",
    artist: String = "", collectible: Boolean = true, playerClass: String = "",
    howToGetGold: String = "", imageUrl: String = "", imageGoldUrl: String = "",
    mechanics: List<String> = listOf("")
) =
    Card(
        id, name, cardSet, type, rarity, cost, attack, health, text, flavor, artist, collectible, playerClass,
        howToGetGold, imageUrl, imageGoldUrl, mechanics
    )
