package com.nielsmasdorp.domain.card.entity

/**
 * Card request used to query specific cards
 */
data class CardRequest(val requestedType: String? = null,
                       val requestedRarity: String? = null,
                       val requestedClass: String? = null,
                       val requestedMechanic: String? = null,
                       val sortingStrategy: Sort = Sort.NONE
) {

    enum class Sort {
        NONE,
        ASCENDING,
        DESCENDING
    }

    companion object {

        /** Constants used in the current version of the app */
        const val LEGENDARY_RARITY = "Legendary"
        const val DEATHRATTLE_MECHANIC = "Deathrattle"
    }
}