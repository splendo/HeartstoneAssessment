package com.nielsmasdorp.heartstone.data.card.model

/**
 * model for a card in the JSON in /assets
 */
data class JSONCard(
        val cardId: String,
        val name: String?,
        val cardSet: String?,
        val type: String?,
        val rarity: String?,
        val cost: Int?,
        val attack: Int?,
        val health: Int?,
        val htmlText: String?,
        val flavour: String?,
        val artist: String?,
        val collectible: String?,
        val elite: Boolean?,
        val playerClass: String?,
        val multiClassGroup: String?,
        val classes: List<String>?,
        val img: String?,
        val imgGold: String?,
        val locale: String?,
        val mechanics: List<JSONCardMechanic>?
)