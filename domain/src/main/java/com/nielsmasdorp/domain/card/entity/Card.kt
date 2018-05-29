package com.nielsmasdorp.domain.card.entity

/**
 * Domain entity for a card used in Heartstone
 */
data class Card(
        val id: String,
        val name: String?,
        val imgUrl: String?,
        val cardSet: String?,
        val type: String?,
        val rarity: String?,
        val cost: Int?,
        val attack: Int?,
        val health: Int?,
        val text: String?,
        val flavor: String?,
        val classes: List<String>?,
        val mechanics: List<String>?)